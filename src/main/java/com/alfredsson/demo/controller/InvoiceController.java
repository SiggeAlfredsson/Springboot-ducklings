package com.alfredsson.demo.controller;

import com.alfredsson.demo.model.Invoice;
import com.alfredsson.demo.repo.InvoiceRepository;
import com.alfredsson.demo.service.InvoiceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    private  InvoiceService invoiceService;

    public InvoiceController( ) {
        invoiceService = new InvoiceService();
    }

    @GetMapping("add")
    public String showAddPaymentPage(Model model) {
        LocalDate today = LocalDate.now();
        model.addAttribute("date", today);
        return "addPaymentPage";
    }

    @PostMapping("add")
    public String addPayment(HttpSession session, Invoice invoice) {

        invoiceService.addInvoice(session, invoice);

        return "redirect:read";
    }

    @GetMapping("read")
    public String showInvoices(Model model, HttpSession session) {

        List<Invoice> invoiceList = invoiceService.getInvoicesForUser(session);

        model.addAttribute("invoiceList", invoiceList);

        return "invoicePage";
    }

    @GetMapping("edit")
    public String showEditPaymentPage(HttpSession session, @RequestParam (name = "editInvoice") int invoiceId, Model model) throws IllegalAccessException {

        Invoice invoice = invoiceService.getInvoiceForEditing(session, invoiceId);

        model.addAttribute("invoice", invoice);

        return "editPaymentPage";
    }

    @PostMapping("edit")
    public String editPayment(Invoice invoice,  @RequestParam ("editInvoice") int invoiceId) {

        invoiceService.updateInvoice(invoiceId, invoice);

        return "redirect:read";
    }

    @PostMapping("delete")
    public String deletePayment(HttpSession session, @RequestParam ("deleteInvoice") int invoiceId) {

        invoiceService.deleteInvoice(session, invoiceId);

        return "redirect:read";
    }

}
