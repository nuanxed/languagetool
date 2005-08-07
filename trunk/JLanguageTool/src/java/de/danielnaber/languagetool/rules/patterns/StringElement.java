/* JLanguageTool, a natural language style checker 
 * Copyright (C) 2005 Daniel Naber (http://www.danielnaber.de)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package de.danielnaber.languagetool.rules.patterns;

/**
 * A part of a pattern that matches one or more words of the text.
 * Typically built from patterns like <code>"a"</code> or <code>"a|the"</code>.
 *
 * @author Daniel Naber
 */
class StringElement extends Element {

  private boolean caseSensitive = false;

  StringElement(String token, boolean caseSensitive) {
    this.tokens = new String[] {token};
    this.caseSensitive = caseSensitive;
  }

  StringElement(String[] tokens, boolean caseSensitive) {
    this.tokens = tokens;
    this.caseSensitive = caseSensitive;
  }
  
  String[] getTokens() {
    return tokens;
  }
  
  /**
   * Get an XPath expression plus an additonal <code>&lt;POS&gt;</code> marker
   * that needs to be replaced with nothing or <code>[1]</code> before this
   * expression is used.
   */
  String getXPathExpression() {
    // Example: t<POS>[.="token1" or .="token2"]
    StringBuffer sb = new StringBuffer();
    sb.append("t<POS>[");
    for (int i = 0; i < tokens.length; i++) {
      if (caseSensitive) {
        sb.append(".=\"" + tokens[i] + "\"");
      } else {
        sb.append("fn:lower-case(.)=\"" + tokens[i].toLowerCase() + "\"");
      }
      if (i < tokens.length-1) {
        sb.append(" or ");
      }
    }
    sb.append("]");
    return sb.toString();
  }

  boolean match(String token) {
    if (caseSensitive) {
      for (int i = 0; i < tokens.length; i++) {
        if (tokens[i].equals(token))
          return true;
      }
    } else {
      for (int i = 0; i < tokens.length; i++) {
        if (tokens[i].equalsIgnoreCase(token))
          return true;
      }
    }
    return false;
  }

}
