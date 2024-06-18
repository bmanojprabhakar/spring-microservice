package ml.backspace.finance.loan.service.impl;

import lombok.AllArgsConstructor;
import ml.backspace.finance.loan.constants.LoansConstants;
import ml.backspace.finance.loan.dto.LoansDto;
import ml.backspace.finance.loan.entity.Loans;
import ml.backspace.finance.loan.exception.LoanAlreadyExistsException;
import ml.backspace.finance.loan.exception.ResourceNotFoundException;
import ml.backspace.finance.loan.mapper.LoansMapper;
import ml.backspace.finance.loan.repository.LoansRepository;
import ml.backspace.finance.loan.service.LoansService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements LoansService {
    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> loans = loansRepository.findByMobileNumber(mobileNumber);
        if(loans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }

        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

        loansRepository.save(newLoan);
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobile number", mobileNumber));
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "Loan number", loansDto.getLoanNumber()));
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobile number", mobileNumber));
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
