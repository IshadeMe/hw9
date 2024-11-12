package org.example;


import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу UPIT из файла contest7_tasks.pdf
     */
    @SneakyThrows
    public void upit(InputStream in, OutputStream out) {
        var treap = new Treap();
        var bi = new BufferedReader(new InputStreamReader(in));
        var bo = new BufferedWriter(new OutputStreamWriter(out));
        var size = Integer.parseInt(bi.readLine().split("\\s+")[1]);
        Arrays.stream(bi.readLine().split("\\s+")).map(Integer::parseInt).forEach(treap::add);

        for (int i = 0; i < size; i++) {
            var s = Arrays.stream(bi.readLine().split("\\s+")).map(Integer::valueOf).collect(Collectors.toList());
            actions.get(s.get(0)).apply(s.get(1), s.get(2), treap, bo, s.size() == 4 ? s.get(3) : null);
        }

    }


    private Map<Integer, Supp<Integer, Treap, BufferedWriter>> actions = Map.of(
            1, (A, B, treap, out, X) -> {
                var origin = treap.split(A - 1);
                if (null != origin[1]) {
                    var middle = origin[1].split(B - A + 1);
                    middle[0].setAll(X);
                    origin[1] = treap.merge(middle[0], middle[1]);
                }
                treap.root = treap.merge(origin[0], origin[1]);
            },
            2, (A, B, treap, out, X) -> {
                var origin = treap.split(A - 1);
                if (null != origin[1]) {
                    var middle = origin[1].split(B - A + 1);
                    var ordered = treap.inorder(middle[0]);
                    int offset;
                    for (int i = 0; i < ordered.size(); i++) {
                        offset = i + 1;
                        ordered.get(i).value += offset * X;
                    }
                    origin[1] = treap.merge(middle[0], middle[1]);
                }
                treap.root = treap.merge(origin[0], origin[1]);
            },
            3, (A, B, treap, out, X) -> {
                treap.add(A - 1, B);
            },
            4, (A, B, treap, out, X) -> {
                var origin = treap.split(A - 1);
                var middle = origin[1].split(B - A + 1);
                out.write(String.format("%d%n", middle[0].getSum()));
                out.flush();
            }
    );

}
