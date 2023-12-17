package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
	
	@Autowired
    private final BeerService beerService;
    
	@PatchMapping("{beerId}")
	public ResponseEntity patchBeerById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
        log.debug("Beer PATCH");
        beerService.patchBeerById(beerId, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT); 
    }
	
    @DeleteMapping("{beerId}")
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {
    	log.debug("Beer DEL");
    	beerService.deleteBeerById(beerId);
    	return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping("{beerId}")
    public ResponseEntity updateById(@PathVariable("beerId")UUID beerId, @RequestBody Beer beer) {
    	log.debug("Beer PUT");
        beerService.updateBeerById(beerId, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping
    public ResponseEntity handlePost(@RequestBody Beer beer) {
        log.debug("Beer POST");
        Beer savedBeer = beerService.saveNewBeer(beer);
        HttpHeaders header = new HttpHeaders();
        header.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());
        return new ResponseEntity(header, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers() {
        log.debug("Beer LIST");
        return beerService.listBeers();
    }

    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("Beer GET by ID");
        return beerService.getBeerById(beerId);
    }
}

