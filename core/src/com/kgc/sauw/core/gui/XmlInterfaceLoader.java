package com.kgc.sauw.core.gui;

import com.kgc.sauw.core.gui.elements.*;
import com.kgc.sauw.core.utils.StringUtils;
import com.kgc.sauw.core.utils.Units;
import com.kgc.sauw.core.utils.languages.Languages;
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

public class XmlInterfaceLoader {
    public static void load(Interface Interface, String xmlString) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
        createFromXml(doc, Interface);
    }

    private static void createFromXml(Document doc, Interface interface_) {
        Element root = doc.getDocumentElement();
        interface_.id = "sauw.interface." + root.getAttribute("id");
        interface_.actionBar.setText(Languages.getString(interface_.id));

        if (Boolean.parseBoolean(root.getAttribute("Inventory"))) InterfaceUtils.createInventory(interface_);

        NodeList RootLayoutList = doc.getElementsByTagName("RootLayout");

        for (int i = 0; i < RootLayoutList.getLength(); i++) {
            if (RootLayoutList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                NodeList n = RootLayoutList.item(i).getChildNodes();
                Element rootLayout = (Element) RootLayoutList.item(i);
                for (int j = 0; j < n.getLength(); j++) {
                    if (n.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element e = (Element) n.item(j);
                        addElement(rootLayout.getAttribute("id"), e, interface_);
                    }
                }
            }
        }
    }

    public static void addElement(String rootLayoutId, Element element, Interface interface_) {
        Layout rootLayout = (Layout) interface_.getElementByFullId(rootLayoutId);

        if (rootLayoutId.equals("MainLayout")) rootLayout = interface_.mainLayout;
        if (rootLayout != null) {
            InterfaceElement interfaceElement = null;
            switch (element.getNodeName()) {
                case "Layout":
                    interfaceElement = new Layout(Layout.Orientation.valueOf(element.getAttribute("orientation")));
                    ((Layout) interfaceElement).setSize(Layout.Size.valueOf(element.getAttribute("width")), Layout.Size.valueOf(element.getAttribute("height")));
                    ((Layout) interfaceElement).setGravity(Layout.Gravity.valueOf(element.getAttribute("gravity")));
                    break;
                case "Button":
                    interfaceElement = new Button("", 0, 0, 0, 0);
                    break;
                case "Text":
                    interfaceElement = new TextView();
                    break;
                case "Image":
                    interfaceElement = new Image();
                    break;
                case "Slot":
                    interfaceElement = new Slot("", interface_);
                    ((Slot) interfaceElement).isInventorySlot = Boolean.parseBoolean(element.getAttribute("isInventorySlot"));
                    break;
                case "EditText":
                    interfaceElement = new EditText();
                    break;
                case "Slider":
                    interfaceElement = new Slider();
                    break;
            }
            if (interfaceElement != null) {
                if (!element.getAttribute("margin").equals("")) {
                    interfaceElement.setMargin(Units.fromStringToFloat(element.getAttribute("margin")));
                }
                if (!element.getAttribute("marginBottom").equals("")) {
                    interfaceElement.setMarginBottom(Units.fromStringToFloat(element.getAttribute("marginBottom")));
                }
                if (!element.getAttribute("marginTop").equals("")) {
                    interfaceElement.setMarginTop(Units.fromStringToFloat(element.getAttribute("marginTop")));
                }
                if (!element.getAttribute("marginRight").equals("")) {
                    interfaceElement.setMarginRight(Units.fromStringToFloat(element.getAttribute("marginRight")));
                }
                if (!element.getAttribute("marginLeft").equals("")) {
                    interfaceElement.setMarginLeft(Units.fromStringToFloat(element.getAttribute("marginLeft")));
                }

                if (!(interfaceElement instanceof Layout)) {
                    if (!element.getAttribute("width").equals("") && !element.getAttribute("height").equals("")) {
                        interfaceElement.setSizeInBlocks(Units.fromStringToFloat(element.getAttribute("width")), Units.fromStringToFloat(element.getAttribute("height")));
                    }
                }

                if (!element.getAttribute("translateX").equals("")) {
                    interfaceElement.setTranslationX(Units.fromStringToFloat(element.getAttribute("translateX")));
                }
                if (!element.getAttribute("translateY").equals("")) {
                    interfaceElement.setTranslationY(Units.fromStringToFloat(element.getAttribute("translateY")));
                }
                if (!element.getAttribute("id").equals("")) {
                    interfaceElement.setId(interface_.id + "." + element.getNodeName().toLowerCase() + "." + element.getAttribute("id"));
                }

                if (interfaceElement instanceof AbstractTextView) {
                    ((AbstractTextView) interfaceElement).setText(StringUtils.getString(element.getAttribute("text")));
                }
                rootLayout.addElements(interfaceElement);
            }
            interface_.updateElementsList();

            if (element.getNodeName().equals("Layout")) {
                NodeList childList = element.getChildNodes();
                for (int i = 0; i < childList.getLength(); i++) {
                    if (childList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Element e = (Element) childList.item(i);
                        addElement(interfaceElement.id, e, interface_);
                    }
                }
            }
        }
        interface_.updateElementsList();
    }
}
