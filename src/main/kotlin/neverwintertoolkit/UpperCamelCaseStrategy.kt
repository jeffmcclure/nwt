package neverwintertoolkit

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@Introspected
@ReflectiveAccess
class UpperCamelCaseStrategy : PropertyNamingStrategies.NamingBase() {
    /**
     * @since 2.14
     */
    //    public final static PropertyNamingStrategies.UpperCamelCaseStrategy INSTANCE
    //            = new PropertyNamingStrategies.UpperCamelCaseStrategy();
    /**
     * Converts camelCase to PascalCase
     *
     *
     * For example, "userName" would be converted to
     * "UserName".
     *
     * @param input formatted as camelCase string
     * @return input converted to PascalCase format
     */
    override fun translate(input: String): String {
        if (input.isEmpty()) {
            return input // garbage in, garbage out
        }
        // Replace first letter with upper-case equivalent
        val c = input[0]
        val uc = c.uppercaseChar()
        if (c == uc) {
            return input
        }
        val sb = StringBuilder(input)
        sb.setCharAt(0, uc)
        return sb.toString()
    }

    companion object {
        private const val serialVersionUID = 2L
    }
}
