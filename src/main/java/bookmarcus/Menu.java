/*
 * This file is part of Bookmarcus.
 *
 * Bookmarcus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bookmarcus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bookmarcus. If not, see <https://www.gnu.org/licenses/>.
 */
package bookmarcus;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author WebCoodi
 * @param <E>
 */
public class Menu<E> {
    
    private final ArrayList<Entry> elements;
    private final HashMap<String, Integer> keyMapper;
    
    public Menu(E defaultElement) {
        this.keyMapper = new HashMap<>();
        this.elements = new ArrayList<>();
        elements.add(0, new Entry("", defaultElement));
    }
    
    public void add(String name, String description, E element) {
        int index = elements.size();
        if (keyMapper.putIfAbsent(name.toLowerCase(), index) != null) {
            throw new IllegalArgumentException("Name already taken");
        }
        keyMapper.put(String.valueOf(index), index);
        elements.add(new Entry(description, element));
    }
    
    public E get(String key) {
        return elements.get(keyMapper.getOrDefault(key.toLowerCase(), 0)).element;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int last = elements.size() - 1;
        for (int i = 1; i < last; i++) {
            sb.append(i).append(") ").append(elements.get(i).description).append("\n");
        }
        sb.append(last).append(") ").append(elements.get(last).description);
        return sb.toString();
    }
    
    private class Entry {
        private final String description;
        private final E element;

        public Entry(String description, E element) {
            this.description = description;
            this.element = element;
        }
    }
    
}
