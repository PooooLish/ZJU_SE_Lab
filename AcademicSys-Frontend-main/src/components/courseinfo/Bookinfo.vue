<template>
    <el-container direction="vertical">
        <div class="bg-container" id="head-container">
            <el-row :gutter="20">
                <el-col :span="6">
                    <el-input v-model="queryBook.textbookId" placeholder="课本编号" autocomplete="off"></el-input>
                </el-col>
                <el-col :span="6">
                    <el-input v-model="queryBook.bookTitle" placeholder="课本名称" autocomplete="off"></el-input>
                </el-col>
                <el-col :span="6">
                    <el-input v-model="queryBook.author" placeholder="作者" autocomplete="off"></el-input>
                </el-col>
                <el-col :span="6">
                    <el-input v-model="queryBook.courseId" placeholder="关联课程编号" autocomplete="off"></el-input>
                </el-col>
            </el-row>
            <div style="height: 10px;"></div>
            <el-row :gutter="20">
                <el-col :span="12">
                    <el-input v-model="queryBook.isbn" placeholder="ISBN编码" autocomplete="off"></el-input>
                </el-col>
                <el-col :span="6">
                    <el-button class="querybutton" icon="el-icon-search" @click="search()">查询</el-button>
                </el-col>
                <el-col :span="6">
                    <el-button @click="addBookDialog()">添加课本</el-button>
                </el-col>
            </el-row>
        </div>

        <div class="bg-container" id="table-container">
            <el-table :data="books" max-height="500px">
                <el-table-column prop="textbookId" label="课本编号" align="center"></el-table-column>
                <el-table-column prop="bookTitle" label="课本名称" align="center"></el-table-column>
                <el-table-column prop="author" label="作者" align="center"></el-table-column>
                <el-table-column prop="edition" label="版本" align="center"></el-table-column>
                <el-table-column prop="isbn" label="ISBN编码" align="center"></el-table-column>
                <el-table-column prop="publisher" label="出版社" align="center"></el-table-column>
                <el-table-column prop="publicationYear" label="出版年份" align="center"></el-table-column>
                <el-table-column prop="courseId" label="关联课程编号" align="center"></el-table-column>
                <el-table-column prop="price" label="价格" align="center"></el-table-column>
                <el-table-column label="操作" width="120px" fixed="right" align="center">
                    <template slot-scope="scope">
                        <div class="editbutton">
                            <el-button class="subbutton" size="mini"
                                @click="editBookDialog(scope.$index)">编辑课本</el-button>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <el-dialog title="添加课本" :visible.sync="addBookDialogVisible" width="40%">
            <el-form :model="newBook">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="课本编号">
                            <el-input v-model="newBook.textbookId" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="课本名称">
                            <el-input v-model="newBook.bookTitle" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="作者">
                            <el-input v-model="newBook.author" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="版本">
                            <el-input v-model="newBook.edition" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="出版社">
                            <el-input v-model="newBook.publisher" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="出版年份">
                            <el-date-picker v-model="newBook.publicationYear" class="picker-width" type="year"
                                placeholder="选择年份" format="yyyy" value-format="yyyy">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="ISBN编码">
                            <el-input v-model="newBook.isbn" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="关联课程编号">
                            <el-input v-model="newBook.courseId" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="价格">
                            <el-input v-model="newBook.price" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="库存">
                            <el-input v-model="newBook.stock" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <el-form-item label="课本图片URL">
                            <el-input v-model="newBook.bookPhoto" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <el-form-item label="课本描述">
                            <el-input v-model="newBook.bookDescription" type="textarea" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addBookDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addBook()">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="编辑课本" :visible.sync="editDialogVisible" width="30%">
            <el-form :model="edits">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="课本编号">
                            <el-input v-model="edits.textbookId" autocomplete="off" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="课本名称">
                            <el-input v-model="edits.bookTitle" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="作者">
                            <el-input v-model="edits.author" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="版本">
                            <el-input v-model="edits.edition" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="出版社">
                            <el-input v-model="edits.publisher" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="出版年份">
                            <el-date-picker v-model="edits.publicationYear" class="picker-width" type="year"
                                placeholder="选择年份" format="yyyy" value-format="yyyy">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="ISBN编码">
                            <el-input v-model="edits.isbn" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="关联课程编号">
                            <el-input v-model="edits.courseId" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="价格">
                            <el-input v-model="edits.price" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="库存">
                            <el-input v-model="edits.stock" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <el-form-item label="课本图片URL">
                            <el-input v-model="edits.bookPhoto" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <el-form-item label="课本描述">
                            <el-input v-model="edits.bookDescription" type="textarea" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button class="left-bottom" type="danger" @click="deleteBookDialog(editingIndex)">删除</el-button>
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="editBook(edits)">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="删除课本" :visible.sync="deleteDialogVisible" width="20%">
            <span>确定删除该课本吗？</span>
            <div slot="footer" class="dialog-footer">
                <el-button @click="deleteDialogVisible = false">取 消</el-button>
                <el-button type="danger" @click="deleteBook()">确 定</el-button>
            </div>
        </el-dialog>

    </el-container>
</template>

<script>
import { Message } from 'element-ui';

export default {
    data() {
        return {
            queryBook: {
                textbookId: null,
                bookTitle: null,
                author: null,
                isbn: null,
                courseId: null
            },
            newBook: {
                textbookId: null,
                bookTitle: null,
                author: null,
                edition: null,
                isbn: null,
                publisher: null,
                publicationYear: null,
                courseId: null,
                price: null,
                bookDescription: null,
                bookPhoto: null,
                stock: null,
                booked: null
            },
            edits: {
                textbookId: null,
                bookTitle: null,
                author: null,
                edition: null,
                isbn: null,
                publisher: null,
                publicationYear: null,
                courseId: null,
                price: null,
                bookDescription: null,
                bookPhoto: null,
                stock: null,
                booked: null
            },
            books: [],
            error: null,
            addBookDialogVisible: false,
            editDialogVisible: false,
            deleteDialogVisible: false,
            editingIndex: null,
            deletingIndex: null
        };
    },
    methods: {
        updateBooks(booksToBeUpdated) {
            console.log(booksToBeUpdated);
            this.books = [];
            for (var i = 0; i < booksToBeUpdated.data.length; i++) {
                this.books.push({
                    textbookId: booksToBeUpdated.data[i].textbookId,
                    bookTitle: booksToBeUpdated.data[i].bookTitle,
                    author: booksToBeUpdated.data[i].author,
                    edition: booksToBeUpdated.data[i].edition,
                    isbn: booksToBeUpdated.data[i].isbn,
                    publisher: booksToBeUpdated.data[i].publisher,
                    publicationYear: booksToBeUpdated.data[i].publicationYear,
                    courseId: booksToBeUpdated.data[i].courseId,
                    price: booksToBeUpdated.data[i].price,
                    bookDescription: booksToBeUpdated.data[i].bookDescription,
                    bookPhoto: booksToBeUpdated.data[i].bookPhoto,
                    stock: booksToBeUpdated.data[i].stock,
                    booked: booksToBeUpdated.data[i].booked
                });
            }
        },
        search() {
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var raw = {};
            // 逐步向 raw 里添加查询条件
            if (this.queryBook.textbookId) {
                raw.textbookId = this.queryBook.textbookId;
            }
            if (this.queryBook.bookTitle) {
                raw.bookTitle = this.queryBook.bookTitle;
            }
            if (this.queryBook.author) {
                raw.author = this.queryBook.author;
            }
            if (this.queryBook.isbn) {
                raw.isbn = this.queryBook.isbn;
            }
            if (this.queryBook.courseId) {
                raw.courseId = this.queryBook.courseId;
            }
            raw = JSON.stringify(raw);

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch("/api/Textbook/getTextbook", requestOptions)
                .then(response => response.text())
                .then(result => this.updateBooks(JSON.parse(result)))
                .catch(error => console.log('error', error));
        },
        addBookDialog() {
            this.addBookDialogVisible = true;
        },
        solveAddBookResult(result) {
            console.log(result);
            if (result.code !== 200) {
                // 显示错误信息
                Message.error(result.msg);
            } else {
                Message.success(result.data);
                // 等待 1s 后关闭对话框
                setTimeout(() => {
                    this.addBookDialogVisible = false;
                    // 刷新当前搜索结果
                    this.search();
                }, 1000);
            }
        },
        addBook() {
            if (!this.newBook.textbookId ||
                !this.newBook.bookTitle ||
                !this.newBook.author ||
                !this.newBook.edition ||
                !this.newBook.isbn ||
                !this.newBook.publisher ||
                !this.newBook.publicationYear ||
                !this.newBook.courseId ||
                !this.newBook.price ||
                !this.newBook.bookDescription ||
                !this.newBook.bookPhoto ||
                !this.newBook.stock) {
                Message.error('所有课程信息均为必填项');
                return;
            }

            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var raw = JSON.stringify({
                textbookId: this.newBook.textbookId,
                bookTitle: this.newBook.bookTitle,
                author: this.newBook.author,
                edition: this.newBook.edition,
                isbn: this.newBook.isbn,
                publisher: this.newBook.publisher,
                publicationYear: this.newBook.publicationYear,
                courseId: this.newBook.courseId,
                price: this.newBook.price,
                bookDescription: this.newBook.bookDescription,
                bookPhoto: this.newBook.bookPhoto,
                stock: this.newBook.stock,
                booked: 0
            });

            console.log(raw);

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch("/api/Textbook/createTextbook", requestOptions)
                .then(response => response.text())
                .then(result => this.solveAddBookResult(JSON.parse(result)))
                .catch(error => console.log('error', error));
        },
        deleteBookDialog(index) {
            this.deletingIndex = index;
            this.deleteDialogVisible = true;
        },
        solveDeleteResult(result) {
            console.log(result);
            if (result.code !== 200) {
                // 显示错误信息
                Message.error(result.msg);
            } else {
                Message.success(result.data);
                // 等待 1s 后关闭对话框
                setTimeout(() => {
                    this.deleteDialogVisible = false;
                    this.editDialogVisible = false;
                    // 刷新当前搜索结果
                    this.search();
                }, 1000);
            }
        },
        deleteBook() {
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var raw = JSON.stringify({
                textbookId: this.books[this.deletingIndex].textbookId
            });

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch("/api/Textbook/delTextbook", requestOptions)
                .then(response => response.text())
                .then(result => this.solveDeleteResult(JSON.parse(result)))
                .catch(error => console.log('error', error));
        },
        editBookDialog(index) {
            this.edits = { ...this.books[index] };
            this.editingIndex = index;
            this.editDialogVisible = true;
        },
        solveEditBookResult(result) {
            console.log(result);
            if (result.code !== 200) {
                // 显示错误信息
                Message.error(result.msg);
            } else {
                Message.success(result.data);
                // 等待 1s 后关闭对话框
                setTimeout(() => {
                    this.editDialogVisible = false;
                    // 刷新当前搜索结果
                    this.search();
                }, 1000);
            }
        },
        editBook(edits) {
            if (!edits.textbookId ||
                !edits.bookTitle ||
                !edits.author ||
                !edits.edition ||
                !edits.isbn ||
                !edits.publisher ||
                !edits.publicationYear ||
                !edits.courseId ||
                !edits.price ||
                !edits.bookDescription ||
                !edits.bookPhoto ||
                !edits.stock) {
                Message.error('所有课程信息均为必填项');
                return;
            }

            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var raw = JSON.stringify({
                textbookId: edits.textbookId,
                bookTitle: edits.bookTitle,
                author: edits.author,
                edition: edits.edition,
                isbn: edits.isbn,
                publisher: edits.publisher,
                publicationYear: edits.publicationYear,
                courseId: edits.courseId,
                price: edits.price,
                bookDescription: edits.bookDescription,
                bookPhoto: edits.bookPhoto,
                stock: edits.stock,
                booked: edits.booked ? edits.booked : 0
            });

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch("/api/Textbook/modTextbook", requestOptions)
                .then(response => response.text())
                .then(result => this.solveEditBookResult(JSON.parse(result)))
                .catch(error => console.log('error', error));
        }
    }
};
</script>

<style scoped src="./info.css"></style>