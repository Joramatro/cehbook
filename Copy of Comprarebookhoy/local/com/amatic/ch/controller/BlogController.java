package com.amatic.ch.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.amatic.ch.constants.WebConstants;
import com.amatic.ch.dto.Publicacion;
import com.amatic.ch.exception.UnknownResourceException;
import com.amatic.ch.utils.WebUtils;

@Controller
public class BlogController extends PublicacionAbstract {

    @RequestMapping(value = { "/blog" }, method = { RequestMethod.GET })
    public String getPublicaciones(ModelMap model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	setPublicaciones(model, WebConstants.SessionConstants.ARTICULO);

	return "blog";
    }

    @RequestMapping(value = { "/extras" }, method = { RequestMethod.GET })
    public String getAccesorios(ModelMap model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	setPublicaciones(model, WebConstants.SessionConstants.ACCESORIO);

	return "extras";
    }

    @RequestMapping(value = { "/ebooks/{url}" }, method = RequestMethod.GET)
    public String cargarPublicacione(ModelMap model, @PathVariable String url,
	    HttpServletRequest request, HttpServletResponse response)
	    throws IOException, NoSuchAlgorithmException {

	setPublicacion(url, request, model);

	return "ebook";
    }

    @RequestMapping(value = { "/ebooks/{url}/nuevoComentario" }, method = { RequestMethod.POST })
    public void guardarComentarioe(ModelMap model,
	    @RequestParam("url") String url,
	    @RequestParam("nombre") String nombre,
	    @RequestParam("email") String email,
	    @RequestParam("puntos") String puntos,
	    @RequestParam("comentario") String comentario,
	    @RequestParam("web") String web,
	    @RequestParam("nbrComment") String nbrComment,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	guardarComentarioPub(request, url, nombre, email, puntos, comentario,
		web, nbrComment, response);

	response.sendRedirect("/ebooks/" + url);

    }

    @RequestMapping(value = { "/ebooks" }, method = { RequestMethod.GET })
    public String getPublicacionese2(ModelMap model,
	    HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	setPublicaciones(model, WebConstants.SessionConstants.EBOOK);

	return "ebooks";
    }

    @RequestMapping(value = { "/blog/{url}" }, method = RequestMethod.GET)
    public String cargarPublicacion2(ModelMap model, @PathVariable String url,
	    HttpServletRequest request, HttpServletResponse response)
	    throws IOException, NoSuchAlgorithmException {

	setPublicacion(url, request, model);

	return "articulo";
    }

    @RequestMapping(value = { "/blog/{url}/nuevoComentario" }, method = { RequestMethod.POST })
    public void guardarComentario2(ModelMap model,
	    @RequestParam("url") String url,
	    @RequestParam("nombre") String nombre,
	    @RequestParam("email") String email,
	    @RequestParam("puntos") String puntos,
	    @RequestParam("comentario") String comentario,
	    @RequestParam("web") String web,
	    @RequestParam("nbrComment") String nbrComment,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	guardarComentarioPub(request, url, nombre, email, puntos, comentario,
		web, nbrComment, response);

	response.sendRedirect("/blog/" + url);

    }

    @RequestMapping(value = { "/venta/{tipo}/{url}" }, method = { RequestMethod.GET })
    public String getVenta(ModelMap model, @PathVariable String url,
	    @PathVariable String tipo, HttpServletRequest request,
	    HttpServletResponse response) throws IOException,
	    NoSuchAlgorithmException {

	String originalUrl = url;
	String keyb = null;
	if (url.endsWith("-2")) {
	    originalUrl = originalUrl.replace("-2", "");
	} else if (url.endsWith("-3")) {
	    originalUrl = originalUrl.replace("-3", "");
	} else if (url.endsWith("-4")) {
	    originalUrl = originalUrl.replace("-4", "");
	} else if (url.endsWith("-5")) {
	    originalUrl = originalUrl.replace("-5", "");
	}

	String key = WebUtils.SHA1(originalUrl.replaceAll("-", " ")
		.toLowerCase());
	Publicacion publicacion = null;
	if (tipo.equals("principal")) {
	    publicacion = publicacionService.getPublicacion(key,
		    WebConstants.SessionConstants.EBOOK);
	    if (publicacion == null) {
		publicacion = publicacionService.getPublicacion(key,
			WebConstants.SessionConstants.ARTICULO);
	    }
	    if (publicacion == null && !originalUrl.equals(url)) {
		keyb = new String(WebUtils.SHA1(url.replaceAll("-", " ")
			.toLowerCase()));
		publicacion = publicacionService.getPublicacion(keyb,
			WebConstants.SessionConstants.EBOOK);
		if (publicacion == null) {
		    publicacion = publicacionService.getPublicacion(keyb,
			    WebConstants.SessionConstants.ARTICULO);
		}
	    }
	} else if (tipo.equals("extra")) {
	    publicacion = publicacionService.getPublicacion(key,
		    WebConstants.SessionConstants.ACCESORIO);
	    if (publicacion == null && !originalUrl.equals(url)) {
		keyb = new String(WebUtils.SHA1(url.replaceAll("-", " ")
			.toLowerCase()));
		publicacion = publicacionService.getPublicacion(keyb,
			WebConstants.SessionConstants.ACCESORIO);
	    }
	} else if (tipo.equals("marca")) {
	    publicacion = new Publicacion();
	    if (url.equals("logo1")) {
		publicacion.setScript(WebConstants.SessionConstants.logo1);
		publicacion.setScript2(WebConstants.SessionConstants.logo1img);
	    } else if (url.equals("logo2")) {
		publicacion.setScript(WebConstants.SessionConstants.logo2);
		publicacion.setScript2(WebConstants.SessionConstants.logo2img);
	    } else if (url.equals("logo3")) {
		publicacion.setScript(WebConstants.SessionConstants.logo3);
		publicacion.setScript2(WebConstants.SessionConstants.logo3img);
	    } else if (url.equals("logo4")) {
		publicacion.setScript(WebConstants.SessionConstants.logo4);
		publicacion.setScript2(WebConstants.SessionConstants.logo4img);
	    } else if (url.equals("logo5")) {
		publicacion.setScript(WebConstants.SessionConstants.logo5);
		publicacion.setScript2(WebConstants.SessionConstants.logo5img);
	    } else if (url.equals("logo6")) {
		publicacion.setScript(WebConstants.SessionConstants.logo6);
		publicacion.setScript2(WebConstants.SessionConstants.logo6img);
	    } else if (url.equals("logo7")) {
		publicacion.setScript(WebConstants.SessionConstants.logo7);
		publicacion.setScript2(WebConstants.SessionConstants.logo7img);
	    } else if (url.equals("logo8")) {
		publicacion.setScript(WebConstants.SessionConstants.logo8);
		publicacion.setScript2(WebConstants.SessionConstants.logo8img);
	    } else if (url.equals("logo9")) {
		publicacion.setScript(WebConstants.SessionConstants.logo9);
		publicacion.setScript2(WebConstants.SessionConstants.logo9img);
	    } else if (url.equals("logo10")) {
		publicacion.setScript(WebConstants.SessionConstants.logo10);
		publicacion.setScript2(WebConstants.SessionConstants.logo10img);
	    } else if (url.equals("kindleFlash")) {
		publicacion
			.setScript(WebConstants.SessionConstants.kindleFlash);
		publicacion
			.setScript2(WebConstants.SessionConstants.kindleFlashimg);
	    } else if (url.equals("preciosIncreibles")) {
		publicacion
			.setScript(WebConstants.SessionConstants.preciosIncreibles);
		publicacion
			.setScript2(WebConstants.SessionConstants.preciosIncreiblesimg);

	    } else if (url.equals("nexus7")) {
		publicacion.setScript(WebConstants.SessionConstants.nexus7);
		publicacion.setScript2(WebConstants.SessionConstants.nexus7img);
	    } else if (url.equals("SonyReader6")) {
		publicacion
			.setScript("http://www.amazon.es/gp/product/B00910TG9G/ref=as_li_ss_tl?ie=UTF8&camp=3626&creative=24822&creativeASIN=B00910TG9G&linkCode=as2&tag=comprarebookh-21");
		publicacion
			.setScript2("http://ir-es.amazon-adsystem.com/e/ir?t=comprarebookh-21&l=as2&o=30&a=B00910TG9G");
	    } else if (url.equals("NookSimpleTouch")) {
		publicacion
			.setScript("http://www.amazon.es/gp/product/140053271X/ref=as_li_ss_tl?ie=UTF8&camp=3626&creative=24822&creativeASIN=140053271X&linkCode=as2&tag=comprarebookh-21");
		publicacion
			.setScript2("http://ir-es.amazon-adsystem.com/e/ir?t=comprarebookh-21&l=as2&o=30&a=140053271X");
	    } else if (url.equals("SonyReaderPRST2")) {
		publicacion
			.setScript("http://www.amazon.es/gp/product/B00910TFSI/ref=as_li_ss_tl?ie=UTF8&camp=3626&creative=24822&creativeASIN=B00910TFSI&linkCode=as2&tag=comprarebookh-21");
		publicacion
			.setScript2("http://ir-es.amazon-adsystem.com/e/ir?t=comprarebookh-21&l=as2&o=30&a=B00910TFSI");
	    } else {
		publicacion = null;
	    }
	} else if (tipo.equals("Papyre602Ocean")) {
	    publicacion = new Publicacion();
	    publicacion
		    .setScript("http://www.amazon.es/gp/product/B00BS9MH80/ref=as_li_ss_tl?ie=UTF8&camp=3626&creative=24822&creativeASIN=B00BS9MH80&linkCode=as2&tag=comprarebookh-21");
	    publicacion
		    .setScript2("http://ir-es.amazon-adsystem.com/e/ir?t=comprarebookh-21&l=as2&o=30&a=B00BS9MH80");
	} else if (tipo.equals("WolderMibukDelta7")) {
	    publicacion = new Publicacion();
	    publicacion
		    .setScript("http://www.amazon.es/gp/product/B0074FPD7Y/ref=as_li_ss_tl?ie=UTF8&camp=3626&creative=24822&creativeASIN=B0074FPD7Y&linkCode=as2&tag=comprarebookh-21");
	    publicacion
		    .setScript2("http://ir-es.amazon-adsystem.com/e/ir?t=comprarebookh-21&l=as2&o=30&a=B0074FPD7Y");
	} else if (tipo.equals("ApproxAPPEB02G")) {
	    publicacion = new Publicacion();
	    publicacion
		    .setScript("http://www.amazon.es/gp/product/B009OCGEW8/ref=as_li_ss_tl?ie=UTF8&camp=3626&creative=24822&creativeASIN=B009OCGEW8&linkCode=as2&tag=comprarebookh-21");
	    publicacion
		    .setScript2("http://ir-es.amazon-adsystem.com/e/ir?t=comprarebookh-21&l=as2&o=30&a=B009OCGEW8");
	} else if (tipo.equals("WolderMiBukKids")) {
	    publicacion = new Publicacion();
	    publicacion
		    .setScript("http://www.amazon.es/gp/product/B008DG53R8/ref=as_li_ss_tl?ie=UTF8&camp=3626&creative=24822&creativeASIN=B008DG53R8&linkCode=as2&tag=comprarebookh-21");
	    publicacion
		    .setScript2("http://ir-es.amazon-adsystem.com/e/ir?t=comprarebookh-21&l=as2&o=30&a=B008DG53R8");
	}
	if (publicacion == null) {
	    String uri = request.getRequestURI();
	    throw new UnknownResourceException("No existe la ruta: " + uri);
	    // return "channelNotFound";
	}

	StringBuffer mensaje = new StringBuffer();
	Enumeration<String> headerNames = request.getHeaderNames();
	boolean existsAccept = false;
	boolean existsCookie = false;
	boolean existsReferer = false;
	boolean condition1 = false;
	boolean condition2 = false;
	boolean condition3 = false;
	boolean condition4 = false;
	while (headerNames.hasMoreElements()) {
	    String headerName = headerNames.nextElement();
	    if (headerName.equals("Accept")) {
		existsAccept = true;
	    }
	    if (headerName.equals("Cookie")) {
		existsCookie = true;
	    }
	    if (headerName.equals("Referer")) {
		existsReferer = true;
	    }

	    mensaje.append(headerName);
	    String headerValue = request.getHeader(headerName);
	    if (headerName.equals("X-AppEngine-Country")
		    && headerValue.equals("US")) {
		condition1 = true;
	    }
	    if (headerName.equals("X-AppEngine-Region")
		    && headerValue.equals("?")) {
		condition2 = true;
	    }
	    if (headerName.equals("X-AppEngine-City")
		    && headerValue.equals("?")) {
		condition3 = true;
	    }
	    if (headerName.equals("X-AppEngine-Country")
		    && headerValue.equals("ZZ")) {
		condition4 = true;
	    }
	    if (headerName.equals("User-Agent")
		    && headerValue.contains("Zookabot")) {
		existsAccept = false;
	    }
	    mensaje.append(", " + headerValue);
	    mensaje.append("\n");
	}
	mensaje.append("ip: " + WebUtils.getClienAddress(request) + "\n");
	if (condition1 && condition2 && condition3 && !existsCookie) {
	    // mensaje.append("NO ENVIADO A VENTAS");
	    // Mail.sendMail(mensaje.toString(), "CEH " +
	    // request.getRequestURI());
	    return null;
	} else if (existsAccept && !existsCookie && !existsReferer
		&& condition4) {
	    // bot chungo
	    // mensaje.append("NO ENVIADO Blue Coat");
	    // Mail.sendMail(mensaje.toString(), "CEH " +
	    // request.getRequestURI());
	    return null;
	} else if (existsAccept) {

	    // Mail.sendMail(mensaje.toString(), "CEH " +
	    // request.getRequestURI());
	    model.addAttribute("publicacion", publicacion);
	    if (keyb != null) {
		String lastTwoChars = url.substring(url.length() - 2);
		if (url.endsWith(lastTwoChars + "-2")) {
		    return "venta/venta2";
		} else if (url.endsWith(lastTwoChars + "-3")) {
		    return "venta/venta3";
		} else if (url.endsWith(lastTwoChars + "-4")) {
		    return "venta/venta4";
		} else if (url.endsWith(lastTwoChars + "-5")) {
		    return "venta/venta5";
		} else {
		    return "venta/venta";
		}
	    } else {

		if (url.endsWith("-2")) {
		    return "venta/venta2";
		} else if (url.endsWith("-3")) {
		    return "venta/venta3";
		} else if (url.endsWith("-4")) {
		    return "venta/venta4";
		} else if (url.endsWith("-5")) {
		    return "venta/venta5";
		} else {
		    return "venta/venta";
		}
	    }
	} else {
	    // mensaje.append("NO ENVIADO A VENTAS POR NO TENER ACCEPT");
	    // Mail.sendMail(mensaje.toString(), "CEH " +
	    // request.getRequestURI());
	    return null;
	}

    }

}
