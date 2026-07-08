# spring-learn — Country REST Service

A small Spring MVC (classic XML configuration) web application exposing two
REST endpoints:

**1. Fixed India lookup**, from a single bean in `spring-servlet.xml`:
```
GET http://localhost:8083/country

Response:
{
    "code": "IN",
    "name": "India"
}
```

**2. Lookup by country code (case-insensitive)**, from a list of beans in
`country.xml`, resolved through `CountryService` using a Java 8 lambda/stream:
```
GET http://localhost:8083/countries/in
GET http://localhost:8083/countries/IN     <- same result, case-insensitive

Response:
{
    "code": "IN",
    "name": "India"
}
```
Unknown codes (e.g. `GET /countries/zz`) return an HTTP `404 Not Found`.

> **Note on the URL:** the requirement's sample request shows the singular
> path `/country/in`, but the required method annotation is
> `@GetMapping("/countries/{code}")` (plural). This project implements the
> annotation exactly as specified, so the actual path is
> `/countries/{code}` (plural) — e.g. `http://localhost:8083/countries/in`.

## Project structure

```
spring-learn/
├── pom.xml
└── src/main
    ├── java/com/cognizant/springlearn
    │   ├── controller/CountryController.java   # /country and /countries/{code}
    │   ├── model/Country.java                  # code/name POJO
    │   └── service/CountryService.java          # case-insensitive lookup (lambda)
    └── webapp/WEB-INF
        ├── web.xml
        ├── spring-servlet.xml                  # component-scan, imports country.xml
        └── country.xml                         # <util:list> of Country beans
```

## How to run

```bash
cd spring-learn
mvn tomcat7:run
```

The embedded Tomcat plugin is configured in `pom.xml` to run on port **8083**,
matching the sample request. Then browse to:

```
http://localhost:8083/country
```

(Alternatively, `mvn package` produces `spring-learn.war`, which can be
dropped into any servlet container's `webapps` folder — just configure
that container to listen on 8083, or adjust the URL to whatever port it uses.)

---

## SME Notes

### 1. What happens in the controller method?

1. A GET request hits `http://localhost:8083/country`.
2. Because `web.xml` maps everything (`/`) to the `DispatcherServlet`,
   the request is routed to Spring's front controller.
3. The `DispatcherServlet` consults its `HandlerMapping` (enabled via
   `<mvc:annotation-driven/>` and `<context:component-scan/>`), which
   knows that `CountryController.getCountryIndia()` is annotated
   `@RequestMapping(value = "/country", method = RequestMethod.GET)`,
   so that method is invoked.
4. Before this ever happens, at **application start-up**, Spring's
   IoC container already read `spring-servlet.xml`, created a
   `Country` object, called `setCode("IN")` and `setName("India")` on
   it (setter injection from the `<property>` tags), and stored it in
   the container as the bean named `india`.
5. That same singleton instance was injected into the controller's
   `india` field via `@Autowired @Qualifier("india")` when the
   controller bean itself was created.
6. `getCountryIndia()` does no computation — it simply **returns the
   already-populated bean** back to Spring MVC.
7. Because the method (and the class, implicitly via
   `@ResponseBody`) is marked `@ResponseBody`, Spring MVC does **not**
   try to resolve a view (like a JSP). Instead it hands the returned
   object to the message-conversion step described next.

### 2. How is the bean converted into a JSON response?

- `<mvc:annotation-driven/>` in `spring-servlet.xml` registers Spring's
  default set of `HttpMessageConverter`s. Because `jackson-databind`
  is on the classpath, one of those converters is
  `MappingJackson2HttpMessageConverter`.
- When the `DispatcherServlet` sees the `@ResponseBody` return value,
  it asks each registered converter, "can you write a `Country` object
  as the response content-type the client is asking for (`Accept`
  header, typically `*/*` or `application/json`)?"
- The Jackson converter says yes, and internally uses Jackson's
  `ObjectMapper` to inspect the `Country` object **via reflection**:
  it finds the public getters `getCode()` and `getName()`, strips the
  `get` prefix and lower-cases the first letter to derive the JSON
  property names `code` and `name`, and writes their values.
- The result is the JSON body:
  ```json
  {"code":"IN","name":"India"}
  ```
- The converter also sets the response header
  `Content-Type: application/json` (usually with `;charset=UTF-8`) so
  the client knows how to interpret the bytes.

In short: **XML config builds and wires the bean → controller returns
the bean → `@ResponseBody` + Jackson's `HttpMessageConverter` turns
that bean into a JSON string on the wire.**

### 3. Viewing HTTP headers in the browser's Network tab

1. Open the URL in Chrome/Edge/Firefox: `http://localhost:8083/country`
2. Open Developer Tools (`F12` or `Ctrl+Shift+I` / `Cmd+Option+I`) and
   go to the **Network** tab.
3. Reload the page (`Ctrl+R` / `Cmd+R`) so the request is captured.
4. Click on the `country` request in the list.
5. Under the **Headers** sub-tab you will see two sections:
   - **Request Headers** — headers the browser sent, e.g.
     `Accept`, `Accept-Encoding`, `Accept-Language`, `Connection`,
     `Host: localhost:8083`, `User-Agent`.
   - **Response Headers** — headers Tomcat/Spring sent back, typically:
     - `Content-Type: application/json;charset=UTF-8`
     - `Content-Length: <n>`
     - `Date: <server date/time>`
     - `Server` / `Servlet` container banner (e.g. `Apache-Coyote/1.1`)
     - `Transfer-Encoding` (if chunked) or `Content-Length` (if not)
6. The **Response** or **Preview** sub-tab shows the actual JSON body
   returned.

### 4. Viewing HTTP headers in Postman

1. Create a new request in Postman: method `GET`, URL
   `http://localhost:8083/country`.
2. Click **Send**.
3. Below the request bar, Postman shows the response with several
   tabs: **Body**, **Cookies**, **Headers**, **Test Results**.
4. Click the **Headers** tab (it usually shows a count badge, e.g.
   "Headers (5)") to see the headers Tomcat/Spring sent back — the
   same ones listed above (`Content-Type`, `Content-Length`, `Date`,
   `Server`, etc.).
5. Postman also lets you inspect the exact request headers it sent by
   clicking the **Code** icon (`</>`) near Send, which generates the
   raw HTTP request including headers.

---

## How `/countries/{code}` works, end to end

1. At application start-up, `spring-servlet.xml` does
   `<import resource="country.xml" />`, which loads `country.xml`.
2. `country.xml` declares a `<util:list id="countryList" ...>` containing
   ten `Country` beans (India, US, UK, Australia, Canada, Germany, France,
   Japan, Singapore, UAE). Spring builds this into a single
   `List<Country>` bean named `countryList`.
3. `CountryService` has:
   ```java
   @Autowired
   @Qualifier("countryList")
   private List<Country> countries;
   ```
   so that list is injected straight into the service.
4. A request like `GET /countries/in` is routed by the `DispatcherServlet`
   to `CountryController.getCountry(@PathVariable String code)`, because
   the method is annotated `@GetMapping("/countries/{code}")`.
   `@PathVariable` extracts `"in"` from the URL and passes it as `code`.
5. The controller delegates: `countryService.getCountry(code)`.
6. `CountryService.getCountry(...)` uses a **lambda expression** with the
   Stream API instead of an explicit loop:
   ```java
   return countries.stream()
           .filter(country -> country.getCode().equalsIgnoreCase(code))
           .findFirst()
           .orElseThrow(() -> new ResponseStatusException(
                   HttpStatus.NOT_FOUND, "No country found for code: " + code));
   ```
   `equalsIgnoreCase` is what makes the match case-insensitive — `"in"`,
   `"IN"`, and `"In"` all match the stored code `"IN"`.
7. The matched `Country` bean is returned back up to the controller, and
   from there Jackson (via `@ResponseBody` / `MappingJackson2HttpMessageConverter`,
   the same mechanism described above for `/country`) serializes it to JSON.

## Notes / gotchas

- **Package naming**: the spec's package
  `com.cognizant.spring-learn.controller` contains a hyphen, which is
  not legal in a Java package name. This project uses
  `com.cognizant.springlearn.controller` instead (project/artifact
  name keeps the hyphen: `spring-learn`).
- Make sure `jackson-databind` is on the classpath — without it,
  Spring has no converter capable of producing JSON, and you'd get a
  `406 Not Acceptable` or the response would fall back to some other
  format.
- The bean is a **singleton** by default, so every request to
  `/country` returns the same shared `Country` instance — fine here
  since it's read-only, immutable-in-practice data.
