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
package database.bookmark;

import java.util.HashSet;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author WebCoodi
 */
public class TypeTest {
    
    @Test
    public void allTypeFieldsAreUnique() {
        HashSet<Integer> ids = new HashSet<>();
        HashSet<String> names = new HashSet<>();
        for (Type type : Type.values()) {
            int id = type.toInt();
            assertFalse(ids.contains(id));
            ids.add(id);
            
            String name = type.toString();
            assertFalse(names.contains(name));
            names.add(name);
        }
    }
    
}
