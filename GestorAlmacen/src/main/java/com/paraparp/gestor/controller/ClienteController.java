package com.paraparp.gestor.controller;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paraparp.gestor.model.entity.Cliente;
import com.paraparp.gestor.service.IClienteService;
import com.paraparp.gestor.service.IUploadFileService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	@Qualifier("clienteServiceImp")
	private IClienteService clienteService;

	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Cliente cliente = clienteService.findById(id);

		if (cliente == null) {

			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");

			return "redirect:/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre());

		return "ver";

	}

	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("titulo", "Lista de clientes");
		model.addAttribute("clientes", clienteService.findAll());

		return "listar";

	}

	@GetMapping("/cancel")
	public String listar() {

		return "redirect:/listar";

	}

	@GetMapping("/prueba")
	public String prueba(Model model) {
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("titulo", "Lista de clientes");

		return "prueba";
	}

	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {

		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de Cliente");
		return "form";
	}

	@PostMapping(value = "/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, SessionStatus status, RedirectAttributes flash) {

		model.addAttribute("titulo", "Formulario de Cliente");
		if (result.hasErrors()) {
			return "form";
		}

		if (!foto.isEmpty()) {

			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) {

				uploadFileService.delete(cliente.getFoto());

			}

			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente " + uniqueFilename + ".");

			cliente.setFoto(uniqueFilename);
		}

		String msgFlash = (cliente.getId() != null) ? "Cliente editado con exito" : "Cliente creado con exito";
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", msgFlash);

		return "redirect:listar";
	}

	@RequestMapping(value = "/form/{id}")
	public String update(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Cliente cliente = null;
		if (id > 0) {

			cliente = clienteService.findById(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "El cliente no existe");
				return "redirect:/listar";
			}

		} else {
			flash.addFlashAttribute("error", "El id del cliente no puede ser cero");
			return "redirect:/listar";
		}
		model.addAttribute("titulo", "Editar cliente");
		model.addAttribute("cliente", cliente);
		return "form";

	}

	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Cliente cliente = clienteService.findById(id);

			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con exito");
			
				if (uploadFileService.delete(cliente.getFoto())) {
					flash.addFlashAttribute("info", cliente.getFoto() + " eliminada con exito.");
				}
			}

		return"redirect:/listar";

}}
