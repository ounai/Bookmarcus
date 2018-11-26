
package io;

import java.util.ArrayDeque;
import java.util.Queue;

public class MockIO implements IO {

    private final Queue<String> input;
    private final Queue<String> output;

    public MockIO() {
        this.input = new ArrayDeque<>();
        this.output = new ArrayDeque<>();
    }

    public void addLines(String... lines) {
        for (String line : lines) {
            input.add(line);
        }
    }

    @Override
    public boolean hasNextLine() {
        return !input.isEmpty();
    }

    @Override
    public String nextLine() {
        return input.poll();
    }

    @Override
    public void print(String... lines) {
        for (String line : lines) {
            output.add(line);
        }
    }

    @Override
    public void close() {
        input.clear();
        output.clear();
    }

}