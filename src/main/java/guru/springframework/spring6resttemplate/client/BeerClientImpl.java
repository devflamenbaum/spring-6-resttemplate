package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;

/*
 *   @author: gabflbm. created on 15/09/2023 !
 */
@Service
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient {

    private final RestTemplateBuilder restTemplateBuilder;

    public static final String GET_BEER_PATH = "/api/v1/beer";
    public static final String GET_BEER_BY_ID_PATH = GET_BEER_PATH + "/{beerId}";

    @Override
    public void deleteBeer(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.delete(GET_BEER_BY_ID_PATH, beerId);
    }

    @Override
    public BeerDTO updateBeer(BeerDTO beerDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.put(GET_BEER_BY_ID_PATH, beerDto, beerDto.getId());
        return getBeerById(beerDto.getId());
    }

    @Override
    public Page<BeerDTO> listBeers() {
        return this.listBeers(null, null, null, null, null);
    }

    @Override
    public Page<BeerDTO> listBeers(
            String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {

        RestTemplate restTemplate = restTemplateBuilder.build();

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH);

        if ( beerName != null) {
            uriComponentsBuilder.queryParam("beerName", beerName);
        }

        if (beerStyle != null) {
            uriComponentsBuilder.queryParam("beerStyle", beerStyle);
        }

        if (showInventory != null) {
            uriComponentsBuilder.queryParam("showInventory", showInventory);
        }

        if (pageNumber != null) {
            uriComponentsBuilder.queryParam("pageNumber", pageNumber);
        }

        if (pageSize != null) {
            uriComponentsBuilder.queryParam("pageSize", pageSize);
        }

//        ResponseEntity<String> stringResponse =
//                restTemplate.getForEntity(BASE_URL + GET_BEER_PATH, String.class);
//
//        ResponseEntity<Map> mapResponse =
//                restTemplate.getForEntity(BASE_URL + GET_BEER_PATH, Map.class);
//
//        ResponseEntity<JsonNode> jsonResponse =
//                restTemplate.getForEntity(BASE_URL + GET_BEER_PATH, JsonNode.class);
//
//        Objects.requireNonNull(jsonResponse.getBody()).findPath("content").elements().forEachRemaining(
//                node -> {
//                    System.out.println(node.get("name").asText());
//                });
//
//        System.out.println(stringResponse.getBody());

        ResponseEntity<BeerDTOPageImpl> response =
                restTemplate.getForEntity( uriComponentsBuilder.toUriString(), BeerDTOPageImpl.class);


        return response.getBody();
    }

    @Override
    public BeerDTO getBeerById(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.getForObject(GET_BEER_BY_ID_PATH, BeerDTO.class, beerId);
    }

    @Override
    public BeerDTO createBeer(BeerDTO beerDTO) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        //ResponseEntity<BeerDTO> response = restTemplate.postForEntity(GET_BEER_PATH, beerDTO, BeerDTO.class);
        URI uri = restTemplate.postForLocation(GET_BEER_PATH, beerDTO);
        return restTemplate.getForObject(Objects.requireNonNull(uri).getPath(), BeerDTO.class);
    }
}
