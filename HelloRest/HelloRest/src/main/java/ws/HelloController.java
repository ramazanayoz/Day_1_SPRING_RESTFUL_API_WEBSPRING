package ws;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

/*********PART1 EN BASİT BASİT ŞEYLER GET POST PUT DELETE İSTEĞİNE CEVAP GÖNDERME*******************************************************************/
	//@RequestParam
	//@RequestMapping
	//@GetMapping
	//@PostMapping
	//@PutMapping
	//@DeleteMapping
	
	//test yapma cmd terminalde::: curl   http://localhost:8080/hello
	@RequestMapping("/hello") //GET http://localhost:8080/hello
	public String hello() {
		return "Hello World"; //cliente gönderilecek veri Hello World
	}
	
	//test yapma cmd terminalde::: curl http://localhost:8080/hello2?name=ramazan
	@GetMapping("/hello2") //GET http://localhost:8080/hello2?name=ramazan
	//public String hello() {
	public String read( @RequestParam(name = "name", defaultValue = "World") String name) { //name valuesi clientden geliyor. @RequestParam ile urldeki param değerini aldık
		return "Hello " + name; //cliente gönderilecek veri Hello Ramazan 
	}
	
	//test yapma cmd terminalden ::: curl -X POST http://localhost:8080/hello
	@PostMapping("/hello") //POST http://localhost:8080/hello
	public String add() {
		return "you have sent POST request"; //cliente gönderilecek veri
	}
	
	//test yapma cmd terminalden ::: curl -X PUT http://localhost:8080/hello
	@PutMapping("/hello") //PUT http://localhost:8080/hello
	public String update() {
		return "you have sent PUT request"; //cliente gönderilecek veri
	}
	
	//test yapma cmd terminalden ::: curl -X DELETE http://localhost:8080/hello
	@DeleteMapping("/hello") //DELETE http://localhost:8080/hello
	public String delete() { 
		return "you have sent DELETE request"; //cliente gönderilecek veri
	}
	 
	
/*************PART2 SERVERDAN CEVAP OLARAK JSON VERİ GÖNDERME VE SERVER'DA, CLİENT'DEN GÖNDERİLEN JSON VERİYİ OKUMA**************EN ÖNEMLİSİ************************************/
	//@GetMapping
	//@PostMapping
	//@RequestBody
	
	
	//*--------CLİENTDEN JSON TİPİNDE GELEN VERİYİ ALMA----Read JSON data from Client(request)-----------*/
	
	//test yapma cmd terminalden hepsini birleşik yaz::: curl -X POST -H "Content-Type: application/json"^
	//  -d "{\"id\":4,\"name\":\"Honda Civic\",\"price\":30000}"^ 
	//  http://localhost:8080/addproduct
	@PostMapping("/addproduct")
	public void addProduct(@RequestBody ProductModel productObj) { //clientden gönderilen veriye @RequestBody ile ulaşılır 
		System.out.println(productObj); //bizim konsolda çıktısı:: ProductModel [id=4, name=Honda Civic, price=30000.0]
	}
	
	
	//*---------SERVEDAN CEVAP OLARAK JSON VERİ GÖNDERME-------WRİTE JSON DATA TO RESPONSE-------------*/
	
	//test yapma terminalden :: curl http://localhost:8080/getproduct
	@GetMapping("/getproduct")  //client GET request yapar http://localhost:8080/getproduct adresine 
	public ProductModel getProduct() { 
		return new ProductModel(1, "İPhone", 999.99f);  //json veri cevap olarak gönderiliyor cliente //client cmd'de çıktısı:: {"id":1,"name":"İPhone","price":999.99}
	}
	
	//test yapma terminalden :: curl http://localhost:8080/getproducts
	@GetMapping("/getproducts") //client GET request yapar http://localhost:8080/getproducts adresine 
	public List<ProductModel> getProducts(){
		List<ProductModel> products = new ArrayList<ProductModel>();
		
		products.add(new ProductModel(1, "Kindle Fire", 98.98f));
		products.add(new ProductModel(2, "XBOX 369", 300.f));
		
		return products; //json veri cevap olarak gönderiliyor cliente //client cmd'de çıktısı:::  [{"id":1,"name":"Kindle Fire","price":98.98},{"id":2,"name":"XBOX 369","price":300.0}]
	}
	
	
/************PART3  CONTENT TYPE AYARLAMA----SPECİFY CONTENT TYPE OF REQUEST/RESPONSE********************************************/	
	//@GetMapping(..)
	
	
	//client cmd terminal'de content type html belirtirtmek zorunda :: //curl -H "Content-Type: text/html" http://localhost:8080/getproduct2
	@GetMapping(
			value = "/getproduct2", //client GET request yapar http://localhost:8080/getproduct2 adresine 
			consumes = {MediaType.TEXT_HTML_VALUE}, // Burda şart koyduk gelen kaynağın Content type'i html olmak zorunda  yoksa clientden yapılan request, server tarafından  kabul edilmez
			produces = {MediaType.APPLICATION_JSON_VALUE} //burda cliente gönderilen verinin json olacağı şartını koymuş olduk
			) 
	public ProductModel getProduct2() {
		return new ProductModel(1, "IPhone", 999.99f);  // cliente json veri cevap olarak gönderiliyor  
		//çıktısı client cmd'de::: {"id":1,"name":"IPhone","price":999.99}
	}
	 
	
	//test yapma terminalden :: curl http://localhost:8080/getproducts3
	@GetMapping(
			value = "/getproduct3", //client GET request yapar http://localhost:8080/getproduct3 adresine 
			produces = {MediaType.TEXT_XML_VALUE} //burda cliente gönderilen verinin xml olacağı şartını koymuş olduk
	) 
	public String getProduct3() {
		return  "<product></product>";  // cliente XML TİPİ veri cevap olarak gönderiliyor   //çıktısı client cmd'de::: <product></product>
		//browser'de de html çıktısını http://localhost:8080/getproducts3 adresinden görebiliriz 
	}
	 
	
	
	
/********PART 4 STATUS CODE AYARLAMA----Specify HTTP status code--**************************************************************************/
	//@GetMapping
	//@PathVariable
	//ResponseEntity<ProductModel>
	
	
	//test yapma cmd terminalden ::: curl -v  http://localhost:8080/products/3
	@GetMapping("/products/{id}") // client GET http://localhost:8080/products/3 request yaparsa bu kısım çalışır
	public ResponseEntity<ProductModel> getProduct5(@PathVariable int id){ // @PathVariable İLE URLDEKİ clientden gönderilen id'yi alır
		if(id == 3) {
			ProductModel productObj = new ProductModel(3, "Kindle Fire", 99.88f);
			return new ResponseEntity<ProductModel>(productObj, HttpStatus.OK ); //cliente gönderilen cevap //çıktısı cmd terminalde ::: {"id":3,"name":"Kindle Fire","price":99.88}* 
		}else {
			return new ResponseEntity<ProductModel>(HttpStatus.NOT_FOUND);  //cliente gönderilen cevap //çıktısı cmd terminalde ::: çıtkı yok ve bağlantı HTTP/1.1 404 hatası verdi 
		}
	}
	
		
}
