package year_2022

import Base2022

class Level7 : Base2022(7) {
    data class MyFile(val name: String, val size: Int)
    data class Folder(
        val name: String,
        val folders: MutableList<Folder> = mutableListOf(),
        val files: MutableList<MyFile> = mutableListOf()
    )

    private fun findPreviousFolder(root: Folder, current: Folder): Folder? {
        root.folders.forEach { folder ->
            if (folder == current) {
                return root
            }
            val prev = findPreviousFolder(folder, current)
            if (prev != null) return prev
        }
        return null
    }

    private fun findSizeOfDirectory(dir: Folder): Int {
        return dir.files.sumOf { it.size } + dir.folders.sumOf { findSizeOfDirectory(it) }
    }

    private fun findSumOfAllDirsBellow100000(root: Folder): Int {
        var count = 0
        root.folders.forEach {
            val dirSum = findSizeOfDirectory(it)
            if (dirSum < 100000) {
                count += dirSum
            }
            count += findSumOfAllDirsBellow100000(it)
        }
        return count
    }

    private fun findFoldersAndTheirSize(root: Folder): Map<Folder, Int> {
        val result = mutableMapOf<Folder, Int>()
        root.folders.forEach {
            result[it] = findSizeOfDirectory(it)

            if (it.folders.size > 0) {
                val res = findFoldersAndTheirSize(it)
                result.putAll(res)
            }
        }

        return result
    }

    var currentFolder = Folder("/")
    val root = currentFolder

    override fun part1() {
        lines.forEach {
            val splitted = it.split(" ")
            val first = splitted[0]
            val second = splitted[1]

            if (first == "$") {
                // Command
                if (second == "ls") {
                    // LS
                } else if (second == "cd") {
                    // CD
                    val third = splitted[2]
                    if (third == "/") return@forEach

                    if (third == "..") {
                        // Going Back
                        currentFolder = findPreviousFolder(root, currentFolder)!!

                    } else {
                        // Enter some folder
                        var folderToEnter = currentFolder.folders.find { founded -> founded.name == third }
                        if (folderToEnter == null) {
                            folderToEnter = Folder(third)
                            currentFolder.folders.add(folderToEnter)
                        }
                        // update the current folder
                        currentFolder = folderToEnter
                    }
                }
            } else {
                // Output of ls
                if (first == "dir") {
                    val dir = Folder(second)
                    currentFolder.folders.add(dir)

                } else {
                    currentFolder.files.add(MyFile(second, first.toInt()))
                }
            }

        }

        println(root)

        println("Sum of all dirs bellow 100000 is ${findSumOfAllDirsBellow100000(root)}")
        val total = findSizeOfDirectory(root)
        println("Sum of root is $total")

        val result = findFoldersAndTheirSize(root)
        val listOfSmall: MutableMap<Folder, Int> = mutableMapOf()
        val diff = 70000000 - total
        result.forEach { (folder, size) ->
            if (30000000 - diff < size) listOfSmall[folder] = size
        }


        val sorted = listOfSmall.toList().sortedBy { findSizeOfDirectory(it.first) }
        sorted.forEach {
            println("${it.first.name}, ${it.second}")
        }

        println("Size of deleted directory is : ${sorted[0].second}")

    }

    override fun part2() {

    }
}