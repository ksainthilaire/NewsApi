package com.ksainthilaire.domain.model.enum

/**
 * Enumeration of supported languages for article sources.
 * @property code The ISO 639-1 code of the language
 */
enum class Language(val code: String) {
    AR("ar"),
    DE("de"),
    EN("en"),
    ES("es"),
    FR("fr"),
    HE("he"),
    IT("it"),
    NL("nl"),
    NO("no"),
    PT("pt"),
    RU("ru"),
    SV("sv"),
    UD("ud"),
    ZH("zh")
}