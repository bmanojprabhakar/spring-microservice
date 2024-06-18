package ml.backspace.finance.loan.service;

import ml.backspace.finance.loan.dto.LoansDto;

public interface LoansService {
    void createLoan(String mobileNumber);

    LoansDto fetchLoan(String mobileNumber);

    boolean updateLoan(LoansDto loansDto);

    boolean deleteLoan(String mobileNumber);
}
