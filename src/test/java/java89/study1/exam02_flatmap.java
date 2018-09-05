package java89.study1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class exam02_flatmap {

    @Test
    public void  flatMap()
    {
        List<String> strings = Arrays.asList("Accola, exsul, et medicina.", "Placidus, clemens tabess foris tractare de secundus, brevis luna.","Vox manducares, tanquam rusticus bromium.");

        List<String[]> collect = strings.stream().map(d -> d.split(" ")).collect(Collectors.toList());
        List<String> collectFlat = strings.stream().map(d -> d.split(" ")).flatMap(d -> Arrays.stream(d)).collect(Collectors.toList());

        System.out.println(collect);
        System.out.println(collectFlat);

    }

}
