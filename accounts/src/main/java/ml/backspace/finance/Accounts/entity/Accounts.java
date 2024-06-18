package ml.backspace.finance.Accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @AllArgsConstructor @ToString @NoArgsConstructor
public class Accounts extends BaseEntity {
    private Long customerId;
    @Id
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
}
