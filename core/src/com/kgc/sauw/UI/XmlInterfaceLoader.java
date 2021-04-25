package com.kgc.sauw.UI;

import com.kgc.sauw.UI.Elements.*;
import com.kgc.sauw.utils.Units;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class XmlInterfaceLoader {
    public static void load(Interface Interface, String xmlString) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
        createFromXml(doc, Interface);
    }

    private static void createFromXml(Document doc, Interface Interface) {
        Element root = doc.getDocumentElement();

        if (root.getAttribute("header").contains("%Language")) {
            Interface.setHeaderText(LANGUAGES.getString(root.getAttribute("header").substring(10)));
        } else {
            Interface.setHeaderText(root.getAttribute("header"));
        }
        Interface.isBlockInterface(Boolean.parseBoolean(root.getAttribute("isBlockInterface")));
        if (Boolean.parseBoolean(root.getAttribute("Inventory"))) Interface.createInventory();

        NodeList RootLayoutList = doc.getElementsByTagName("RootLayout");

        for (int i = 0; i < RootLayoutList.getLength(); i++) {
            if (RootLayoutList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                NodeList n = RootLayoutList.item(i).getChildNodes();
                Element rootLayout = (Element) RootLayoutList.item(i);
                for (int j = 0; j < n.getLength(); j++) {
                    if (n.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element e = (Element) n.item(j);
                        addElement(rootLayout.getAttribute("id"), e, Interface);
                        /*if (e.getNodeName().equals("Layout")) {
                            NodeList childNodes = e.getChildNodes();
                            for (int k = 0; k < childNodes.getLength(); k++) {
                                if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                    Element childElement = (Element) childNodes.item(k);
                                    addElement(e.getAttribute("id"), childElement, Interface);
                                }
                            }
                        }*/
                    }
                }
            }
        }
    }

    public static void addElement(String rootLayoutId, Element element, Interface Interface) {
        Layout rootLayout = null;
        for (InterfaceElement e : Interface.Elements) {
            if (e.ID != null) {
                System.out.println(Interface.ID + "_" + rootLayoutId);
                if (e instanceof Layout && e.ID.equals(Interface.ID + "_" + rootLayoutId)) rootLayout = (Layout) e;
            }
        }
        if (rootLayout != null) {
            InterfaceElement InterfaceElement = null;
            switch (element.getNodeName()) {
                case "Layout":
                    InterfaceElement = new Layout(Layout.Orientation.valueOf(element.getAttribute("orientation")));
                    ((Layout) InterfaceElement).setSize(Layout.Size.valueOf(element.getAttribute("width")), Layout.Size.valueOf(element.getAttribute("height")));
                    ((Layout) InterfaceElement).setGravity(Layout.Gravity.valueOf(element.getAttribute("gravity")));
                    break;
                case "Button":
                    InterfaceElement = new Button("", 0, 0, 0, 0);
                    break;
                case "Text":
                    InterfaceElement = new Text();
                    break;
                case "Image":
                    InterfaceElement = new Image(0, 0, 0, 0);
                    break;
                case "Slot":
                    InterfaceElement = new Slot("", Interface, 0, 0, 0, 0);
                    ((Slot) InterfaceElement).isInventorySlot = Boolean.parseBoolean(element.getAttribute("isInventorySlot"));
                    break;
            }
            if (InterfaceElement != null) {
                if (!element.getAttribute("margin").equals("")) {
                    InterfaceElement.setMargin(Units.fromStringToPx(element.getAttribute("margin")));
                }
                if (!element.getAttribute("marginBottom").equals("")) {
                    InterfaceElement.setMarginBottom(Units.fromStringToPx(element.getAttribute("marginBottom")));
                }
                if (!element.getAttribute("marginTop").equals("")) {
                    InterfaceElement.setMarginTop(Units.fromStringToPx(element.getAttribute("marginTop")));
                }
                if (!element.getAttribute("marginRight").equals("")) {
                    InterfaceElement.setMarginRight(Units.fromStringToPx(element.getAttribute("marginRight")));
                }
                if (!element.getAttribute("marginLeft").equals("")) {
                    InterfaceElement.setMarginLeft(Units.fromStringToPx(element.getAttribute("marginLeft")));
                }

                if (!(InterfaceElement instanceof Layout)) {
                    if (!element.getAttribute("width").equals("") && !element.getAttribute("height").equals("")) {
                        InterfaceElement.setSize(Units.fromStringToPx(element.getAttribute("width")), Units.fromStringToPx(element.getAttribute("height")));
                    }
                }

                if (!element.getAttribute("translateX").equals("")) {
                    InterfaceElement.setTranslationX(Units.fromStringToPx(element.getAttribute("translateX")));
                }
                if (!element.getAttribute("translateY").equals("")) {
                    InterfaceElement.setTranslationY(Units.fromStringToPx(element.getAttribute("translateY")));
                }

                if (!element.getAttribute("id").equals("")) {
                    InterfaceElement.setID(Interface.ID + "_" + element.getAttribute("id"));
                }
            }

            if (InterfaceElement != null) {
                rootLayout.addElements(InterfaceElement);
            }
            Interface.updateElementsList();
            if (element.getNodeName().equals("Layout")) {
                NodeList childList = element.getChildNodes();
                for (int i = 0; i < childList.getLength(); i++) {
                    if (childList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Element e = (Element) childList.item(i);
                        addElement(element.getAttribute("id"), e, Interface);
                    }
                }
            }
        }
        Interface.updateElementsList();
    }
}
