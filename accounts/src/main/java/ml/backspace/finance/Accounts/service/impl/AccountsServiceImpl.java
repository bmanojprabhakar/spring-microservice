package ml.backspace.finance.Accounts.service.impl;

import lombok.AllArgsConstructor;
import ml.backspace.finance.Accounts.constants.AccountsConstants;
import ml.backspace.finance.Accounts.dto.AccountsDto;
import ml.backspace.finance.Accounts.dto.CustomerDto;
import ml.backspace.finance.Accounts.entity.Accounts;
import ml.backspace.finance.Accounts.entity.Customer;
import ml.backspace.finance.Accounts.exception.CustomerAlreadyExistsException;
import ml.backspace.finance.Accounts.exception.ResourceNotFoundException;
import ml.backspace.finance.Accounts.mapper.AccountsMapper;
import ml.backspace.finance.Accounts.mapper.CustomerMapper;
import ml.backspace.finance.Accounts.repository.AccountsRepository;
import ml.backspace.finance.Accounts.repository.CustomerRepository;
import ml.backspace.finance.Accounts.service.AccountsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements AccountsService {
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("customer", "mobileNumber", mobileNumber));

        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customer ID", String.valueOf(customer.getCustomerId())));

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account, new AccountsDto()));
        CustomerMapper.mapToCustomerDto(customer, customerDto);
        return customerDto;
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();

        if(null != accountsDto) {
            Accounts accounts = accountsRepository.findById(customerDto.getAccountsDto().getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "Account number", customerDto.getAccountsDto().getAccountNumber().toString()));

            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accountsRepository.save(accounts);

            Customer customer = customerRepository.findById(accounts.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "Customer ID", accounts.getCustomerId().toString()));
            CustomerMapper.mapToCustomer(customerDto, customer);

            customerRepository.save(customer);

            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteCustomer(String mobileNumber) {
        boolean isDeleted = false;

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber));

        if(null != customer) {
            customerRepository.delete(customer);
            accountsRepository.deleteByCustomerId(customer.getCustomerId());
            isDeleted = true;
        }
        return isDeleted;
    }


    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedBy("Anonymous");
        newAccount.setCreatedAt(LocalDateTime.now());
        return newAccount;
    }
}
