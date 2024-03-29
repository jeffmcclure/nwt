package neverwintertoolkit.model.dlg

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.annotation.JsonNaming
import neverwintertoolkit.UpperCamelCaseStrategy

/**
 * Simplified DLG format
 */
@JsonNaming(UpperCamelCaseStrategy::class)
class Dlgs : DlgsNpcResponse() {
    var endConverAbort: String? = null
    var endConversation: String? = null
    var preventZoomIn: Boolean = false
    override fun toString(): String {
        return "DlgSimpRoot(endConverAbort=$endConverAbort, endConversation=$endConversation, preventZoomIn=$preventZoomIn)"
    }
}

@JsonNaming(UpperCamelCaseStrategy::class)
open class DlgsNpcResponse {
    @get:JsonIgnore
    @set:JsonIgnore
    var index: Int? = null
    var npcSays: String? = null
    var appearIfScript: String? = null
    var onAppearScript: String? = null
    var sound: String? = null
    var userChoices: ArrayList<DlgsPcChoice>? = null
    override fun toString(): String {
        return "DlgSimpResponse(npcSays=$npcSays, appearIfScript=$appearIfScript, onAppearScript=$onAppearScript, sound=$sound, userChoices=$userChoices, index=$index)"
    }
}

@JsonNaming(UpperCamelCaseStrategy::class)
class DlgsPcChoice {
    @get:JsonIgnore
    @set:JsonIgnore
    var index: Int? = null
    var userSays: String? = null
    var appearIfScript: String? = null
    var onSelectScript: String? = null
    var response: DlgsNpcResponse? = null
    override fun toString(): String {
        return "DlgSimpUserChoice(index=$index, userSays=$userSays, appearIfScript=$appearIfScript, onSelectScript=$onSelectScript, response=$response)"
    }
}
