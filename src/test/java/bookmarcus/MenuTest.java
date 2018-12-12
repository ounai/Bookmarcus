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

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author WebCoodi
 */
public class MenuTest {
    
    Object defaultElement;
    Menu<Object> menu;
    
    @Before
    public void setUp() {
        defaultElement = new Object();
        menu = new Menu(defaultElement);
    }
    
    @Test
    public void DefaultReturnedIfNoKey() {
        assertSame(defaultElement, menu.get("key"));
    }
    
    @Test
    public void NamesMustBeUnique() {
        try {
            menu.add("name", "", new Object());
            menu.add("name", "", new Object());
            fail();
        } catch (IllegalArgumentException e) {}
    }
    
    @Test
    public void CanGetAddedElement() {
        Object o = new Object();
        menu.add("test", "", o);
        assertSame(o, menu.get("1"));
        assertSame(o, menu.get("test"));
    }
    
    @Test
    public void ItemsAppearInOrder() {
        Object first = new Object();
        Object second = new Object();
        Object third = new Object();
        menu.add("first", "", first);
        menu.add("second", "", second);
        menu.add("third", "", third);
        assertSame(first, menu.get("first"));
        assertSame(second, menu.get("second"));
        assertSame(third, menu.get("third"));
    }
    
}
