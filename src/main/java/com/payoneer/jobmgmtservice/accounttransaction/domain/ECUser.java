package com.payoneer.jobmgmtservice.accounttransaction.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ananth Shanmugam 
 * Entity class for generate account transaction job
 */
@NamedQueries({
        @NamedQuery(name = "ECUser.findByUsername", query = "SELECT o FROM ECUser o WHERE o.username = (?1)"),
        @NamedQuery(name = "ECUser.findByIdNoAndIdType", query = "SELECT o FROM ECUser o WHERE o.idNo = (?1) AND o.idType = (?2)") })
@Entity
@Table(name = "ECUSER")
@Setter
@Getter
@NoArgsConstructor
@Data
public class ECUser {

    @Id
    @Column(name = "USERID", unique = true)
    private String userId;

    @Column(name = "USERNAME", unique = true, length = 32)
    private String username;

    @Column(name = "NAME", length = 200)
    private String name;

    @Column(name = "IDTYPE", length = 20)
    private String idType;

    @Column(name = "IDNO", length = 20)
    private String idNo;

    @Column(name = "ATMCARDNO", length = 20)
    private String atmCardNo;


}
