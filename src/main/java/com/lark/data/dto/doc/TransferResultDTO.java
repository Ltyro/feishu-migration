package com.lark.data.dto.doc;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TransferResultDTO {
    private int total;
    private int file;
    private int fileNone;
    private int fileDone;
    private int fileFail;
    private int fileDownloading;
    private int fileDownloadDone;
    private int fileDownloadFail;
    private int fileUploadDone;
    private int fileUploadFail;
    private int doc;
    private int docNone;
    private int docDone;
    private int docFail;
    private int folder;
    private int folderNone;
    private int folderDone;
    private int folderFail;
    private int sheet;
    private int sheetDone;
    private int sheetNone;
    private int sheetFail;
    private int bitTable;
    private int bitTableDone;
    private int bitTableNone;
    private int bitTableFail;
    private int otherOnline;
    private int otherOnlineDone;
    private int otherOnlineNone;
    private int otherOnlineFail;

    TransferResultDTO(final int total, final int file, final int fileNone, final int fileDone, final int fileFail, final int fileDownloading, final int fileDownloadDone, final int fileDownloadFail, final int fileUploadDone, final int fileUploadFail, final int doc, final int docNone, final int docDone, final int docFail, final int folder, final int folderNone, final int folderDone, final int folderFail, final int sheet, final int sheetDone, final int sheetNone, final int sheetFail, final int bitTable, final int bitTableDone, final int bitTableNone, final int bitTableFail, final int otherOnline, final int otherOnlineDone, final int otherOnlineNone, final int otherOnlineFail) {
        this.total = total;
        this.file = file;
        this.fileNone = fileNone;
        this.fileDone = fileDone;
        this.fileFail = fileFail;
        this.fileDownloading = fileDownloading;
        this.fileDownloadDone = fileDownloadDone;
        this.fileDownloadFail = fileDownloadFail;
        this.fileUploadDone = fileUploadDone;
        this.fileUploadFail = fileUploadFail;
        this.doc = doc;
        this.docNone = docNone;
        this.docDone = docDone;
        this.docFail = docFail;
        this.folder = folder;
        this.folderNone = folderNone;
        this.folderDone = folderDone;
        this.folderFail = folderFail;
        this.sheet = sheet;
        this.sheetDone = sheetDone;
        this.sheetNone = sheetNone;
        this.sheetFail = sheetFail;
        this.bitTable = bitTable;
        this.bitTableDone = bitTableDone;
        this.bitTableNone = bitTableNone;
        this.bitTableFail = bitTableFail;
        this.otherOnline = otherOnline;
        this.otherOnlineDone = otherOnlineDone;
        this.otherOnlineNone = otherOnlineNone;
        this.otherOnlineFail = otherOnlineFail;
    }

    public static TransferResultDTOBuilder builder() {
        return new TransferResultDTOBuilder();
    }

    public int getTotal() {
        return this.total;
    }

    public int getFile() {
        return this.file;
    }

    public int getFileNone() {
        return this.fileNone;
    }

    public int getFileDone() {
        return this.fileDone;
    }

    public int getFileFail() {
        return this.fileFail;
    }

    public int getFileDownloading() {
        return this.fileDownloading;
    }

    public int getFileDownloadDone() {
        return this.fileDownloadDone;
    }

    public int getFileDownloadFail() {
        return this.fileDownloadFail;
    }

    public int getFileUploadDone() {
        return this.fileUploadDone;
    }

    public int getFileUploadFail() {
        return this.fileUploadFail;
    }

    public int getDoc() {
        return this.doc;
    }

    public int getDocNone() {
        return this.docNone;
    }

    public int getDocDone() {
        return this.docDone;
    }

    public int getDocFail() {
        return this.docFail;
    }

    public int getFolder() {
        return this.folder;
    }

    public int getFolderNone() {
        return this.folderNone;
    }

    public int getFolderDone() {
        return this.folderDone;
    }

    public int getFolderFail() {
        return this.folderFail;
    }

    public int getSheet() {
        return this.sheet;
    }

    public int getSheetDone() {
        return this.sheetDone;
    }

    public int getSheetNone() {
        return this.sheetNone;
    }

    public int getSheetFail() {
        return this.sheetFail;
    }

    public int getBitTable() {
        return this.bitTable;
    }

    public int getBitTableDone() {
        return this.bitTableDone;
    }

    public int getBitTableNone() {
        return this.bitTableNone;
    }

    public int getBitTableFail() {
        return this.bitTableFail;
    }

    public int getOtherOnline() {
        return this.otherOnline;
    }

    public int getOtherOnlineDone() {
        return this.otherOnlineDone;
    }

    public int getOtherOnlineNone() {
        return this.otherOnlineNone;
    }

    public int getOtherOnlineFail() {
        return this.otherOnlineFail;
    }

    public void setTotal(final int total) {
        this.total = total;
    }

    public void setFile(final int file) {
        this.file = file;
    }

    public void setFileNone(final int fileNone) {
        this.fileNone = fileNone;
    }

    public void setFileDone(final int fileDone) {
        this.fileDone = fileDone;
    }

    public void setFileFail(final int fileFail) {
        this.fileFail = fileFail;
    }

    public void setFileDownloading(final int fileDownloading) {
        this.fileDownloading = fileDownloading;
    }

    public void setFileDownloadDone(final int fileDownloadDone) {
        this.fileDownloadDone = fileDownloadDone;
    }

    public void setFileDownloadFail(final int fileDownloadFail) {
        this.fileDownloadFail = fileDownloadFail;
    }

    public void setFileUploadDone(final int fileUploadDone) {
        this.fileUploadDone = fileUploadDone;
    }

    public void setFileUploadFail(final int fileUploadFail) {
        this.fileUploadFail = fileUploadFail;
    }

    public void setDoc(final int doc) {
        this.doc = doc;
    }

    public void setDocNone(final int docNone) {
        this.docNone = docNone;
    }

    public void setDocDone(final int docDone) {
        this.docDone = docDone;
    }

    public void setDocFail(final int docFail) {
        this.docFail = docFail;
    }

    public void setFolder(final int folder) {
        this.folder = folder;
    }

    public void setFolderNone(final int folderNone) {
        this.folderNone = folderNone;
    }

    public void setFolderDone(final int folderDone) {
        this.folderDone = folderDone;
    }

    public void setFolderFail(final int folderFail) {
        this.folderFail = folderFail;
    }

    public void setSheet(final int sheet) {
        this.sheet = sheet;
    }

    public void setSheetDone(final int sheetDone) {
        this.sheetDone = sheetDone;
    }

    public void setSheetNone(final int sheetNone) {
        this.sheetNone = sheetNone;
    }

    public void setSheetFail(final int sheetFail) {
        this.sheetFail = sheetFail;
    }

    public void setBitTable(final int bitTable) {
        this.bitTable = bitTable;
    }

    public void setBitTableDone(final int bitTableDone) {
        this.bitTableDone = bitTableDone;
    }

    public void setBitTableNone(final int bitTableNone) {
        this.bitTableNone = bitTableNone;
    }

    public void setBitTableFail(final int bitTableFail) {
        this.bitTableFail = bitTableFail;
    }

    public void setOtherOnline(final int otherOnline) {
        this.otherOnline = otherOnline;
    }

    public void setOtherOnlineDone(final int otherOnlineDone) {
        this.otherOnlineDone = otherOnlineDone;
    }

    public void setOtherOnlineNone(final int otherOnlineNone) {
        this.otherOnlineNone = otherOnlineNone;
    }

    public void setOtherOnlineFail(final int otherOnlineFail) {
        this.otherOnlineFail = otherOnlineFail;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof TransferResultDTO)) {
            return false;
        } else {
            TransferResultDTO other = (TransferResultDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getTotal() != other.getTotal()) {
                return false;
            } else if (this.getFile() != other.getFile()) {
                return false;
            } else if (this.getFileNone() != other.getFileNone()) {
                return false;
            } else if (this.getFileDone() != other.getFileDone()) {
                return false;
            } else if (this.getFileFail() != other.getFileFail()) {
                return false;
            } else if (this.getFileDownloading() != other.getFileDownloading()) {
                return false;
            } else if (this.getFileDownloadDone() != other.getFileDownloadDone()) {
                return false;
            } else if (this.getFileDownloadFail() != other.getFileDownloadFail()) {
                return false;
            } else if (this.getFileUploadDone() != other.getFileUploadDone()) {
                return false;
            } else if (this.getFileUploadFail() != other.getFileUploadFail()) {
                return false;
            } else if (this.getDoc() != other.getDoc()) {
                return false;
            } else if (this.getDocNone() != other.getDocNone()) {
                return false;
            } else if (this.getDocDone() != other.getDocDone()) {
                return false;
            } else if (this.getDocFail() != other.getDocFail()) {
                return false;
            } else if (this.getFolder() != other.getFolder()) {
                return false;
            } else if (this.getFolderNone() != other.getFolderNone()) {
                return false;
            } else if (this.getFolderDone() != other.getFolderDone()) {
                return false;
            } else if (this.getFolderFail() != other.getFolderFail()) {
                return false;
            } else if (this.getSheet() != other.getSheet()) {
                return false;
            } else if (this.getSheetDone() != other.getSheetDone()) {
                return false;
            } else if (this.getSheetNone() != other.getSheetNone()) {
                return false;
            } else if (this.getSheetFail() != other.getSheetFail()) {
                return false;
            } else if (this.getBitTable() != other.getBitTable()) {
                return false;
            } else if (this.getBitTableDone() != other.getBitTableDone()) {
                return false;
            } else if (this.getBitTableNone() != other.getBitTableNone()) {
                return false;
            } else if (this.getBitTableFail() != other.getBitTableFail()) {
                return false;
            } else if (this.getOtherOnline() != other.getOtherOnline()) {
                return false;
            } else if (this.getOtherOnlineDone() != other.getOtherOnlineDone()) {
                return false;
            } else if (this.getOtherOnlineNone() != other.getOtherOnlineNone()) {
                return false;
            } else {
                return this.getOtherOnlineFail() == other.getOtherOnlineFail();
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TransferResultDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getTotal();
        result = result * 59 + this.getFile();
        result = result * 59 + this.getFileNone();
        result = result * 59 + this.getFileDone();
        result = result * 59 + this.getFileFail();
        result = result * 59 + this.getFileDownloading();
        result = result * 59 + this.getFileDownloadDone();
        result = result * 59 + this.getFileDownloadFail();
        result = result * 59 + this.getFileUploadDone();
        result = result * 59 + this.getFileUploadFail();
        result = result * 59 + this.getDoc();
        result = result * 59 + this.getDocNone();
        result = result * 59 + this.getDocDone();
        result = result * 59 + this.getDocFail();
        result = result * 59 + this.getFolder();
        result = result * 59 + this.getFolderNone();
        result = result * 59 + this.getFolderDone();
        result = result * 59 + this.getFolderFail();
        result = result * 59 + this.getSheet();
        result = result * 59 + this.getSheetDone();
        result = result * 59 + this.getSheetNone();
        result = result * 59 + this.getSheetFail();
        result = result * 59 + this.getBitTable();
        result = result * 59 + this.getBitTableDone();
        result = result * 59 + this.getBitTableNone();
        result = result * 59 + this.getBitTableFail();
        result = result * 59 + this.getOtherOnline();
        result = result * 59 + this.getOtherOnlineDone();
        result = result * 59 + this.getOtherOnlineNone();
        result = result * 59 + this.getOtherOnlineFail();
        return result;
    }

    public String toString() {
        return "TransferResultDTO(total=" + this.getTotal() + ", file=" + this.getFile() + ", fileNone=" + this.getFileNone() + ", fileDone=" + this.getFileDone() + ", fileFail=" + this.getFileFail() + ", fileDownloading=" + this.getFileDownloading() + ", fileDownloadDone=" + this.getFileDownloadDone() + ", fileDownloadFail=" + this.getFileDownloadFail() + ", fileUploadDone=" + this.getFileUploadDone() + ", fileUploadFail=" + this.getFileUploadFail() + ", doc=" + this.getDoc() + ", docNone=" + this.getDocNone() + ", docDone=" + this.getDocDone() + ", docFail=" + this.getDocFail() + ", folder=" + this.getFolder() + ", folderNone=" + this.getFolderNone() + ", folderDone=" + this.getFolderDone() + ", folderFail=" + this.getFolderFail() + ", sheet=" + this.getSheet() + ", sheetDone=" + this.getSheetDone() + ", sheetNone=" + this.getSheetNone() + ", sheetFail=" + this.getSheetFail() + ", bitTable=" + this.getBitTable() + ", bitTableDone=" + this.getBitTableDone() + ", bitTableNone=" + this.getBitTableNone() + ", bitTableFail=" + this.getBitTableFail() + ", otherOnline=" + this.getOtherOnline() + ", otherOnlineDone=" + this.getOtherOnlineDone() + ", otherOnlineNone=" + this.getOtherOnlineNone() + ", otherOnlineFail=" + this.getOtherOnlineFail() + ")";
    }

    public static class TransferResultDTOBuilder {
        private int total;
        private int file;
        private int fileNone;
        private int fileDone;
        private int fileFail;
        private int fileDownloading;
        private int fileDownloadDone;
        private int fileDownloadFail;
        private int fileUploadDone;
        private int fileUploadFail;
        private int doc;
        private int docNone;
        private int docDone;
        private int docFail;
        private int folder;
        private int folderNone;
        private int folderDone;
        private int folderFail;
        private int sheet;
        private int sheetDone;
        private int sheetNone;
        private int sheetFail;
        private int bitTable;
        private int bitTableDone;
        private int bitTableNone;
        private int bitTableFail;
        private int otherOnline;
        private int otherOnlineDone;
        private int otherOnlineNone;
        private int otherOnlineFail;

        TransferResultDTOBuilder() {
        }

        public TransferResultDTOBuilder total(final int total) {
            this.total = total;
            return this;
        }

        public TransferResultDTOBuilder file(final int file) {
            this.file = file;
            return this;
        }

        public TransferResultDTOBuilder fileNone(final int fileNone) {
            this.fileNone = fileNone;
            return this;
        }

        public TransferResultDTOBuilder fileDone(final int fileDone) {
            this.fileDone = fileDone;
            return this;
        }

        public TransferResultDTOBuilder fileFail(final int fileFail) {
            this.fileFail = fileFail;
            return this;
        }

        public TransferResultDTOBuilder fileDownloading(final int fileDownloading) {
            this.fileDownloading = fileDownloading;
            return this;
        }

        public TransferResultDTOBuilder fileDownloadDone(final int fileDownloadDone) {
            this.fileDownloadDone = fileDownloadDone;
            return this;
        }

        public TransferResultDTOBuilder fileDownloadFail(final int fileDownloadFail) {
            this.fileDownloadFail = fileDownloadFail;
            return this;
        }

        public TransferResultDTOBuilder fileUploadDone(final int fileUploadDone) {
            this.fileUploadDone = fileUploadDone;
            return this;
        }

        public TransferResultDTOBuilder fileUploadFail(final int fileUploadFail) {
            this.fileUploadFail = fileUploadFail;
            return this;
        }

        public TransferResultDTOBuilder doc(final int doc) {
            this.doc = doc;
            return this;
        }

        public TransferResultDTOBuilder docNone(final int docNone) {
            this.docNone = docNone;
            return this;
        }

        public TransferResultDTOBuilder docDone(final int docDone) {
            this.docDone = docDone;
            return this;
        }

        public TransferResultDTOBuilder docFail(final int docFail) {
            this.docFail = docFail;
            return this;
        }

        public TransferResultDTOBuilder folder(final int folder) {
            this.folder = folder;
            return this;
        }

        public TransferResultDTOBuilder folderNone(final int folderNone) {
            this.folderNone = folderNone;
            return this;
        }

        public TransferResultDTOBuilder folderDone(final int folderDone) {
            this.folderDone = folderDone;
            return this;
        }

        public TransferResultDTOBuilder folderFail(final int folderFail) {
            this.folderFail = folderFail;
            return this;
        }

        public TransferResultDTOBuilder sheet(final int sheet) {
            this.sheet = sheet;
            return this;
        }

        public TransferResultDTOBuilder sheetDone(final int sheetDone) {
            this.sheetDone = sheetDone;
            return this;
        }

        public TransferResultDTOBuilder sheetNone(final int sheetNone) {
            this.sheetNone = sheetNone;
            return this;
        }

        public TransferResultDTOBuilder sheetFail(final int sheetFail) {
            this.sheetFail = sheetFail;
            return this;
        }

        public TransferResultDTOBuilder bitTable(final int bitTable) {
            this.bitTable = bitTable;
            return this;
        }

        public TransferResultDTOBuilder bitTableDone(final int bitTableDone) {
            this.bitTableDone = bitTableDone;
            return this;
        }

        public TransferResultDTOBuilder bitTableNone(final int bitTableNone) {
            this.bitTableNone = bitTableNone;
            return this;
        }

        public TransferResultDTOBuilder bitTableFail(final int bitTableFail) {
            this.bitTableFail = bitTableFail;
            return this;
        }

        public TransferResultDTOBuilder otherOnline(final int otherOnline) {
            this.otherOnline = otherOnline;
            return this;
        }

        public TransferResultDTOBuilder otherOnlineDone(final int otherOnlineDone) {
            this.otherOnlineDone = otherOnlineDone;
            return this;
        }

        public TransferResultDTOBuilder otherOnlineNone(final int otherOnlineNone) {
            this.otherOnlineNone = otherOnlineNone;
            return this;
        }

        public TransferResultDTOBuilder otherOnlineFail(final int otherOnlineFail) {
            this.otherOnlineFail = otherOnlineFail;
            return this;
        }

        public TransferResultDTO build() {
            return new TransferResultDTO(this.total, this.file, this.fileNone, this.fileDone, this.fileFail, this.fileDownloading, this.fileDownloadDone, this.fileDownloadFail, this.fileUploadDone, this.fileUploadFail, this.doc, this.docNone, this.docDone, this.docFail, this.folder, this.folderNone, this.folderDone, this.folderFail, this.sheet, this.sheetDone, this.sheetNone, this.sheetFail, this.bitTable, this.bitTableDone, this.bitTableNone, this.bitTableFail, this.otherOnline, this.otherOnlineDone, this.otherOnlineNone, this.otherOnlineFail);
        }

        public String toString() {
            return "TransferResultDTO.TransferResultDTOBuilder(total=" + this.total + ", file=" + this.file + ", fileNone=" + this.fileNone + ", fileDone=" + this.fileDone + ", fileFail=" + this.fileFail + ", fileDownloading=" + this.fileDownloading + ", fileDownloadDone=" + this.fileDownloadDone + ", fileDownloadFail=" + this.fileDownloadFail + ", fileUploadDone=" + this.fileUploadDone + ", fileUploadFail=" + this.fileUploadFail + ", doc=" + this.doc + ", docNone=" + this.docNone + ", docDone=" + this.docDone + ", docFail=" + this.docFail + ", folder=" + this.folder + ", folderNone=" + this.folderNone + ", folderDone=" + this.folderDone + ", folderFail=" + this.folderFail + ", sheet=" + this.sheet + ", sheetDone=" + this.sheetDone + ", sheetNone=" + this.sheetNone + ", sheetFail=" + this.sheetFail + ", bitTable=" + this.bitTable + ", bitTableDone=" + this.bitTableDone + ", bitTableNone=" + this.bitTableNone + ", bitTableFail=" + this.bitTableFail + ", otherOnline=" + this.otherOnline + ", otherOnlineDone=" + this.otherOnlineDone + ", otherOnlineNone=" + this.otherOnlineNone + ", otherOnlineFail=" + this.otherOnlineFail + ")";
        }
    }
}
