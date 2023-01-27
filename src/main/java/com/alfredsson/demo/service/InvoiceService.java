package com.alfredsson.demo.service;

import com.alfredsson.demo.model.Invoice;
import com.alfredsson.demo.repo.InvoiceRepository;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class InvoiceService {

    private InvoiceRepository invoiceRepository;

    public InvoiceService() {
        this.invoiceRepository = new InvoiceRepository();
    }


    public Invoice getInvoiceForEditing(HttpSession session, int invoiceId) throws IllegalAccessException {
        Invoice invoice = invoiceRepository.findInvoiceById(invoiceId);
        if (!invoice.getOwner_username().equals(session.getAttribute("username"))) {
            throw new IllegalAccessException("You do not have permission to edit this invoice.");
        }
        return invoice;
    }

    public List<Invoice> getInvoicesForUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        return invoiceRepository.getInvoice(username);
    }

    public void addInvoice (HttpSession session, Invoice invoice) {
        String username = (String) session.getAttribute("username");

        invoiceRepository.addInvoice(username, invoice.getTitle(), invoice.getDate(), invoice.getDescription(), invoice.getCategory(), invoice.getPrice());
    }

    public void updateInvoice(int invoiceId, Invoice invoice) {
        invoiceRepository.updateInvoice(invoiceId,invoice.getTitle(), invoice.getDate(), invoice.getDescription(), invoice.getCategory(), invoice.getPrice());
    }

    public void deleteInvoice(HttpSession session, int invoiceId) {
        String username = (String) session.getAttribute("username");
        invoiceRepository.deleteInvoice(username, invoiceId);
    }


}
