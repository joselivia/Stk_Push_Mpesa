package com.jose.Stk_Mpesa.Request;

public class MpesaRequest {
        private String phoneNumber;
        private String amount;
        private String accountReference;
        private String transactionDesc;

        // Getters and Setters
        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAccountReference() {
            return accountReference;
        }

        public void setAccountReference(String accountReference) {
            this.accountReference = accountReference;
        }

        public String getTransactionDesc() {
            return transactionDesc;
        }

        public void setTransactionDesc(String transactionDesc) {
            this.transactionDesc = transactionDesc;
        }
    }