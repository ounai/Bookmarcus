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

/**
 * The type of an article, either physical or digital.
 *
 * @author WebCoodi
 */
class Article extends Bookmark {
    
    @Override
    public Type getType() {
        return Type.ARTICLE;
    }

    @Override
    public boolean hasAuthor() {
        return true;
    }

    @Override
    public boolean hasISBN() {
        return false;
    }

    @Override
    public boolean hasURL() {
        return true;
    }
    
}
