package br.com.shopeasy.shopeasy.validate


class ValidateCpf(private var cpf: String?) {

    private fun calculateDigit(cpf: String, factor: Int): Int {
        var cpfList = cpf.toList()
        var countFactor = factor
        var total = 0
        for (digit in cpfList){
            if (countFactor > 1) total += digit.toString().toInt() * countFactor--
        }
        val rest = total % 11
        return if (rest < 2) 0 else 11 - rest
    }

    private fun clean(cpf: String): String{
        return cpf.replace(".", "").replace("-", "").replace(" ", "")
    }

    private fun isValidLength(cpf: String): Boolean{
        return cpf.length != 11
    }

    private fun allDigitsTheSame(cpf: String): Boolean{
        return cpf.all { it == cpf[0] }
    }

    private fun extractCheckDigit(cpf: String): String {
        return cpf.substring(cpf.length - 2)
    }

    fun isValidCpf(): Boolean {
        if (cpf?.isEmpty() == true) return false
        val cpf = clean(cpf!!)
        if (isValidLength(cpf)) return false
        if (allDigitsTheSame(cpf)) return false
        val digit1 = calculateDigit(cpf, 10)
        val digit2 = calculateDigit(cpf, 11)
        val actualDigit = extractCheckDigit(cpf)
        val calculateDigit = "$digit1$digit2"
        return actualDigit == calculateDigit

    }

}