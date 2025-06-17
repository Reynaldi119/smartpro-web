package com.juaracoding.smartpro_web.util;

import java.util.List;
import java.util.Map;

public class GenerateStringMenu {
    private StringBuilder sBuilder = new StringBuilder();

    public String stringMenu(List<Map<String,Object>> lt){
        sBuilder.setLength(0);
        for(Map<String, Object> map : lt) {
            // get parent menu data
            Map<String, Object> parentMenu = (Map<String, Object>) map.get("parentMenu");
            List<Map<String, Object>> subMenuList = (List<Map<String, Object>>) map.get("subMenu");
            
            if(subMenuList == null) {
                // parsing parent menu
                sBuilder
                    .append("<li class=\"sidebar-item\"><a href=\"").append(parentMenu.get("menuPath").toString()).append("\" class=\"sidebar-link\">")
                    .append("<i data-feather=\"").append(parentMenu.get("featherIconTag").toString()).append("\" width=\"20\"></i>")
                    .append("<span>").append(parentMenu.get("menuName").toString()).append("</span>")
                    .append("</a></li>");
            }
            else {
                // parsing parent menu
                sBuilder
                    .append("<li class=\"sidebar-item has-sub\"><a href=\"#\" class=\"sidebar-link\">")
                    .append("<i data-feather=\"").append(parentMenu.get("featherIconTag").toString()).append("\" width=\"20\"></i>")
                    .append("<span>").append(parentMenu.get("menuName").toString()).append("</span>")
                    .append("</a>")
                    .append("<ul class=\"submenu\">");

                // parsing sub menu
                for (Map<String, Object> subMenuItem : subMenuList) {
                    sBuilder
                        .append("<li><a class=\"sidebar-link\" href=\"").append(subMenuItem.get("path")).append("\">")
                        .append("<i data-feather=\"").append(subMenuItem.get("featherIconTag").toString()).append("\"  width=\"20\"></i>")
                        .append("<span>").append(subMenuItem.get("name").toString()).append("</span>")
                        .append("</a></li>");
                }
                sBuilder.append("</ul></li>");
            }
        }
        return sBuilder.toString();
    }
}
