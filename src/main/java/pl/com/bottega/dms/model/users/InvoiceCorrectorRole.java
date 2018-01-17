package pl.com.bottega.dms.model.users;

import java.util.ArrayList;
import java.util.Collection;

public class InvoiceCorrectorRole extends UserRole {

    private Collection<String> correctedInvoices = new ArrayList<>();

    public void invoiceCorrected(String nr) {
        correctedInvoices.add(nr);
    }

}
