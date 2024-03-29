package neverwintertoolkit.model.dlg

class DlgSorter(val dlg: Dlg) {
    val entries = mutableListOf<DlgEntry>()
    val replies = mutableListOf<DlgReply>()

    val done = HashSet<Any>()

    fun processEntry(entry: DlgEntry) {
        if (done.contains(entry)) return
        done.add(entry)
        entries.add(entry)
        entry.repliesList?.reversed()?.forEach { foo: DlgFoo ->
            processReply(dlg.replyList!![foo.index!!.toInt()])
        }
    }

    fun processReply(reply: DlgReply) {
        if (done.contains(reply)) return
        done.add(reply)
        replies.add(reply)
        reply.entriesList?.reversed()?.forEach { foo: DlgFoo ->
            processEntry(dlg.entryList!![foo.index!!.toInt()])
        }
    }

    fun renumberEntry(entry: DlgEntry) {
        entries.add(entry)
        entry.repliesList?.forEach { foo: DlgFoo ->
            renumberReply(dlg.replyList!![foo.index!!.toInt()])
        }
    }

    fun renumberReply(reply: DlgReply) {
        replies.add(reply)
        reply.entriesList?.forEach { foo: DlgFoo ->
            renumberEntry(dlg.entryList!![foo.index!!.toInt()])
        }
    }

    fun newReplyIndex(oldIndex: UInt?): UInt {
        return replies.indexOf(dlg.replyList!![oldIndex!!.toInt()]).toUInt()
    }

    fun newEntryIndex(oldIndex: UInt?): UInt {
        return entries.indexOf(dlg.entryList!![oldIndex!!.toInt()]).toUInt()
    }

    fun sorted(): Dlg {
//        val startingList: List<DlgStarting> =
        dlg.startingList!!.reversed().forEach { starting ->
            processEntry(dlg.entryList!![starting.index!!.toInt()])
            starting.index = newEntryIndex(starting.index)
//            starting
        }

//        dlg.startingList!!.reversed().map {
//            renumberEntry(dlg.entryList!![it.index!!.toInt()])
//        }

        entries.forEach { entry ->
            entry.repliesList?.forEach { foo ->
                foo.index = newReplyIndex(foo.index)
            }
        }

        replies.forEach { entry ->
            entry.entriesList?.forEach { foo ->
                foo.index = newEntryIndex(foo.index)
            }
        }

        return Dlg().also {
            it.delayEntry = dlg.delayEntry
            it.delayReply = dlg.delayReply
            it.endConversation = dlg.endConversation
            it.endConverAbort = dlg.endConverAbort
            it.numWords = dlg.numWords
            it.preventZoomIn = dlg.preventZoomIn
            it.entryList = entries
            it.replyList = replies
            it.startingList = dlg.startingList
        }
    }
}