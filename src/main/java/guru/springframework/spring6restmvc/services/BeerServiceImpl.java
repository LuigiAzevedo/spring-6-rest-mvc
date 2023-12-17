package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Beer;

import guru.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.HashMap;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    private final Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        Beer galaxyCat = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer crank = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("1234567")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer sunshineCity = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12345678")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        this.beerMap = new HashMap<>();
        beerMap.put(galaxyCat.getId(), galaxyCat);
        beerMap.put(crank.getId(), crank);
        beerMap.put(sunshineCity.getId(), sunshineCity);
    }
    
	@Override
	public void patchBeerById(UUID beerId, Beer beer) {
		Beer existing = beerMap.get(beerId);
		
		if (StringUtils.hasText(beer.getBeerName())) {
			existing.setBeerName(beer.getBeerName());
		}
		
		if (beer.getBeerStyle()!= null) {
            existing.setBeerStyle(beer.getBeerStyle());
        }
		
		if (beer.getPrice()!= null) {
            existing.setPrice(beer.getPrice());
        }
		
		if (beer.getQuantityOnHand()!= null) {
            existing.setQuantityOnHand(beer.getQuantityOnHand());
		}
        
		if (StringUtils.hasText(beer.getUpc())) {
			existing.setUpc(beer.getUpc());
		}
	}
    
    @Override
	public void deleteBeerById(UUID beerId) {
		beerMap.remove(beerId);
	}
    
	@Override
    public void updateBeerById(UUID beerId, Beer beer) {
		Beer existing = beerMap.get(beerId);
		existing.setBeerName(beer.getBeerName());
		existing.setPrice(beer.getPrice());
		existing.setUpc(beer.getUpc());
		existing.setQuantityOnHand(beer.getQuantityOnHand());
		existing.setUpdateDate(LocalDateTime.now());
		
		beerMap.put(beerId, existing);
	}
    
    @Override
    public Beer saveNewBeer(Beer beer) {
        Beer savedBeer = Beer.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .version(beer.getVersion())
                .build();
        beerMap.put(savedBeer.getId(), savedBeer);
        return savedBeer;
    }

    @Override
    public List<Beer> listBeers() {
        log.debug("listBeers Service");
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer getBeerById(UUID id) {
        log.debug("getBeerById Service. ID: " + id.toString());
        return beerMap.get(id);
    }
}
