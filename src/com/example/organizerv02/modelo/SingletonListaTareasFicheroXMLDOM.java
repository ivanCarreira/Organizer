package com.example.organizerv02.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import android.content.Context;
import android.util.Log;

import com.example.organizerv02.modelo.datos.TareaVO;

public class SingletonListaTareasFicheroXMLDOM implements ListaTareasInterfaz {
	private static SingletonListaTareasFicheroXMLDOM INSTANCE;
	private static String FICHERO = "listaTareas.xml";
	private Context context;
	private Document documento;
	private boolean  cargadoDocumento;
	private Vector<TareaVO> cacheTareas = new Vector<TareaVO>();
	
	private SingletonListaTareasFicheroXMLDOM(Context context){
		this.context = context;
		cargadoDocumento = false;
		actualizarCacheListaTareas();
	}
	
	public static SingletonListaTareasFicheroXMLDOM getInstance(Context context){
		if  (INSTANCE == null){
			INSTANCE = new SingletonListaTareasFicheroXMLDOM(context);
		}
		return INSTANCE;
	}
	
	@Override
	public void guardarTarea(TareaVO tarea) {
		try {
			if (!cargadoDocumento) {
				cargarDocumentoXML(context.openFileInput(FICHERO));
			}
		} catch (FileNotFoundException e) {
			crearDocumentoXML();
		} catch (Exception e) {
			Log.e(this.getClass().toString(), e.getMessage());
		}
		
		nuevaTarea(tarea);
		
		try{
			escribirXMLenDisco(context.openFileOutput(FICHERO, Context.MODE_PRIVATE));
			Log.d(this.getClass().toString(), "Se ha escrito el fichero" + FICHERO);
		} catch (Exception  e)  {
			Log.e(this.getClass().toString(), e.getMessage());
		}
		actualizarCacheListaTareas();
	}

	@Override
	public void eliminarTarea(int idTarea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return cacheTareas.size();
	}

	@Override
	public TareaVO getTarea(int position) {
		// TODO Auto-generated method stub
		return cacheTareas.elementAt(position);
	}

	@Override
	public void actualizarTarea(TareaVO tarea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizarCacheListaTareas() {
		cacheTareas.removeAllElements();
		try {
			if (!cargadoDocumento) {
				cargarDocumentoXML (context.openFileInput(FICHERO));
			}
		} catch (FileNotFoundException e) {
			crearDocumentoXML();
			Log.d(this.getClass().toString(), "Se ha creado el fichero "+FICHERO);
		} catch (Exception e) {
			Log.e(this.getClass().toString(), e.getMessage());
		}
		
		cacheTareas = aVectorTareas();
	}
	
	private void crearDocumentoXML(){
		try {
			DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
			DocumentBuilder constructor = fabrica.newDocumentBuilder();
			
			documento = constructor.newDocument();
			
			Element raiz = documento.createElement("listaTareas");
			documento.appendChild(raiz);
			
			cargadoDocumento = true;
		} catch (Exception e) {
			Log.e(this.getClass().toString(), e.getMessage());
		}
	}
	
	private void cargarDocumentoXML(InputStream entrada) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
		DocumentBuilder constructor = fabrica.newDocumentBuilder();
		
		documento = constructor.parse(entrada);
		cargadoDocumento = true;
	}
	
	private void nuevaTarea(TareaVO tarea) {
		Element elementoTarea = documento.createElement("tarea");
		elementoTarea.setAttribute("idTarea", String.valueOf(tarea.getIdTarea())); //No tiene sentido usarlo ya que tendría que generar la integridad
		
		Element elementoNombre = documento.createElement("nombre");
		Text texto = documento.createTextNode(tarea.getNombreTarea());
		elementoNombre.appendChild(texto);
		
		elementoTarea.appendChild(elementoNombre);
		
		Element elementoFecha = documento.createElement("fecha");
		texto = documento.createTextNode(String.valueOf(tarea.getFecha()));
		elementoFecha.appendChild(texto);
		
		elementoTarea.appendChild(elementoFecha);
		
		Element raiz = documento.getDocumentElement();
		raiz.appendChild(elementoTarea);		
	}
	
	private Vector<TareaVO> aVectorTareas() { //
		Vector<TareaVO> tareas = new Vector<TareaVO>();
		
		Element raiz = documento.getDocumentElement();
		NodeList listaNodosTarea = raiz.getElementsByTagName("tarea");
		for (int i = 0; i < listaNodosTarea.getLength(); i++) {
			TareaVO tarea = new TareaVO();
			Node nodoTarea = listaNodosTarea.item(i);
			NodeList listaNodosHijos = nodoTarea.getChildNodes();
			for (int j = 0; j < listaNodosHijos.getLength(); j++) {
				Node hijo = listaNodosHijos.item(j);
				String etiqueta = hijo.getNodeName();
				if (etiqueta.equals("nombre")) {
					Log.d(this.getClass().toString(), hijo.getFirstChild().getNodeValue());
					tarea.setNombreTarea(hijo.getFirstChild().getNodeValue());
				} else if (etiqueta.equals("fecha")) {
					Log.d(this.getClass().toString(), hijo.getFirstChild().getNodeValue());
					tarea.setFecha(Integer.parseInt(hijo.getFirstChild().getNodeValue()));
				}
			}
			tareas.add(tarea);
		}
		for (int i = 0; i < tareas.size(); i++){
			Log.d(this.getClass().toString(), "Tarea: " + tareas.get(i).getNombreTarea());
		}
		return tareas;
	}
	
	private void escribirXMLenDisco (OutputStream salida) throws TransformerException {
		TransformerFactory fabrica = TransformerFactory.newInstance();
		Transformer transformador = fabrica.newTransformer();
		transformador.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformador.setOutputProperty(OutputKeys.INDENT, "yes");
		
		DOMSource fuente = new DOMSource(documento);
		
		StreamResult streamResultado = new StreamResult(salida);
		
		transformador.transform(fuente, streamResultado);
	}
}

