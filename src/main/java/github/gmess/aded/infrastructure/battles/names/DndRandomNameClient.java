package github.gmess.aded.infrastructure.battles.names;

import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Random;

@Component
public class DndRandomNameClient {

    @Value("${random.dnd.name.endpoint}")
    private String nameGeneratorEndpoint;
    private final RestTemplate rest;

    public DndRandomNameClient(final RestTemplateBuilder restBuilder) {
        this.rest = Objects.requireNonNull(restBuilder.build());
    }

    public String[] fetchRandomDndNames() {
        return Try.of(
                () -> rest.postForObject(
                        nameGeneratorEndpoint,
                        null,
                        String[].class
                ))
                .getOrElse(defaultArrayName);
    }

    public String shuffleResultAndGetRandom(String[] results) {
        final var random = new Random().nextInt(results.length);
        return results[random];
    }

    private static final String[] defaultArrayName = {
            "Tractockus",
            "Wirsag",
            "Twonzo",
            "Eorington",
            "Tigum",
            "Prandita",
            "Ordo",
            "Dalibella",
            "Hortaketta",
            "Alash Mortonium",
            "Tulietta",
            "Herlim",
            "Anadora the Lost",
            "Lostling",
            "Endolinha",
            "Odanti",
            "Zardinark",
            "Arlasta Nupple",
            "Gruzanelle",
            "Kepvan Whistle",
            "Trystrame",
            "Aegis",
            "Envol",
            "Plotton",
            "Zardinark",
            "Jepotah Curdlish",
            "Wilgonston",
            "Oddam Flim",
            "Qyator",
            "Frandomi"
    };
}
