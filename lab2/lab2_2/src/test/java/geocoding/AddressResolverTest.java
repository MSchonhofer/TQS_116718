package geocoding;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    @Mock
    ISimpleHttpClient httpClient;
    @InjectMocks
    AddressResolver resolver;

    @BeforeEach
    void setUp() {
        this.resolver = new AddressResolver(httpClient);
    }

    @Test
    void whenResolveDetiGps_returnJacintoMagalhaeAddress() throws ParseException, IOException, URISyntaxException {

        String response = "{\n" +
                "  \"info\": {\n" +
                "    \"statuscode\": 0,\n" +
                "    \"copyright\": {\n" +
                "      \"text\": \"© 2023 MapQuest, Inc.\",\n" +
                "      \"imageUrl\": \"http://api.mqcdn.com/res/mqlogo.gif\",\n" +
                "      \"imageAltText\": \"© 2023 MapQuest, Inc.\"\n" +
                "    },\n" +
                "    \"messages\": []\n" +
                "  },\n" +
                "  \"options\": {\n" +
                "    \"maxResults\": 1,\n" +
                "    \"thumbMaps\": true,\n" +
                "    \"ignoreLatLngInput\": false\n" +
                "  },\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"providedLocation\": {\n" +
                "        \"latLng\": {\n" +
                "          \"lat\": 30.333472,\n" +
                "          \"lng\": -81.470448\n" +
                "        }\n" +
                "      },\n" +
                "      \"locations\": [\n" +
                "        {\n" +
                "          \"street\": \"Avenida João Jacinto de Magalhães\",\n" +
                "          \"adminArea6\": \"\",\n" +
                "          \"adminArea6Type\": \"Neighborhood\",\n" +
                "          \"adminArea5\": \"Aveiro\",\n" +
                "          \"adminArea5Type\": \"City\",\n" +
                "          \"adminArea4\": \"Duval\",\n" +
                "          \"adminArea4Type\": \"County\",\n" +
                "          \"adminArea3\": \"\",\n" +
                "          \"adminArea3Type\": \"State\",\n" +
                "          \"adminArea1\": \"PT\",\n" +
                "          \"adminArea1Type\": \"Country\",\n" +
                "          \"postalCode\": \"3810-149\",\n" +
                "          \"geocodeQualityCode\": \"B1AAA\",\n" +
                "          \"geocodeQuality\": \"ADDRESS\",\n" +
                "          \"dragPoint\": false,\n" +
                "          \"sideOfStreet\": \"R\",\n" +
                "          \"linkId\": \"0\",\n" +
                "          \"unknownInput\": \"\",\n" +
                "          \"type\": \"s\",\n" +
                "          \"latLng\": {\n" +
                "            \"lat\": 30.33472,\n" +
                "            \"lng\": -81.470448\n" +
                "          },\n" +
                "          \"displayLatLng\": {\n" +
                "            \"lat\": 30.333472,\n" +
                "            \"lng\": -81.470448\n" +
                "          },\n" +
                "          \"mapUrl\": \"http://www.mapquestapi.com/staticmap/v4/getmap?key=KEY&type=map&size=225,160&pois=purple-1,30.3334721,-81.4704483,0,0,|&center=30.3334721,-81.4704483&zoom=15&rand=-553163060\",\n" +
                "          \"nearestIntersection\": {\n" +
                "            \"streetDisplayName\": \"Posey Cir\",\n" +
                "            \"distanceMeters\": \"851755.1608527573\",\n" +
                "            \"latLng\": {\n" +
                "              \"longitude\": -87.523761,\n" +
                "              \"latitude\": 35.013434\n" +
                "            },\n" +
                "            \"label\": \"Danley Rd & Posey Cir\"\n" +
                "          },\n" +
                "          \"roadMetadata\": {\n" +
                "            \"speedLimitUnits\": \"mph\",\n" +
                "            \"tollRoad\": null,\n" +
                "            \"speedLimit\": 40\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        when(httpClient.doHttpGet(any(String.class))).thenReturn(response);

        Optional<Address> result = resolver.findAddressForLocation(40.633116, -8.658784);

        //return
        Address expected = new Address("Avenida João Jacinto de Magalhães", "Aveiro", "", "3810-149", null);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());

    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {

        assertThrows(IllegalArgumentException.class, () -> {
            resolver.findAddressForLocation(-361, -361);
        });
    }
}