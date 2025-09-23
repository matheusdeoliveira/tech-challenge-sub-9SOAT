package br.com.fiap.reseller_motors.domain.vo;

public final class CPF {
    private final String value;

    public CPF(String raw) {
        String digits = raw == null ? "" : raw.replaceAll("\\D", "");
        if (!isValidCPF(digits)) throw new IllegalArgumentException("Invalid CPF");
        this.value = digits;
    }

    public String getValue() {
        return value;
    }

    private boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) return false;
        if (cpf.chars().distinct().count() == 1) return false;
        try {
            int d1 = 0, d2 = 0;
            for (int i = 0; i < 9; i++) {
                int n = cpf.charAt(i) - '0';
                d1 += n * (10 - i);
                d2 += n * (11 - i);
            }
            d1 = 11 - (d1 % 11);
            if (d1 >= 10) d1 = 0;
            d2 += d1 * 2;
            d2 = 11 - (d2 % 11);
            if (d2 >= 10) d2 = 0;
            return (cpf.charAt(9) - '0') == d1 && (cpf.charAt(10) - '0') == d2;
        } catch (Exception e) {
            return false;
        }
    }
}