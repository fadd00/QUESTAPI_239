package com.sample.prak12.database

import android.util.Log
import com.sample.prak12.modeldata.DataSiswa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class DatabaseHelper {
    companion object {
        // ⚠️ KONFIGURASI DATABASE - SESUAIKAN DENGAN SETUP ANDA
        private const val HOST = "192.168.1.30"  // IP laptop Anda
        private const val PORT = "3306"           // Port MariaDB default
        private const val DATABASE = "TIUMY" // Ganti dengan nama database Anda
        private const val USERNAME = "root"       // Username MariaDB
        private const val PASSWORD = ""           // Password MariaDB (kosongkan jika tidak ada)

        private const val URL = "jdbc:mysql://$HOST:$PORT/$DATABASE?useSSL=false&allowPublicKeyRetrieval=true"

        private const val TAG = "DatabaseHelper"
    }

    /**
     * Mendapatkan koneksi ke database
     */
    private suspend fun getConnection(): Connection? = withContext(Dispatchers.IO) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
            DriverManager.getConnection(URL, USERNAME, PASSWORD)
        } catch (e: Exception) {
            Log.e(TAG, "Error connecting to database: ${e.message}", e)
            null
        }
    }

    /**
     * Mengambil semua data siswa dari database
     */
    suspend fun getAllSiswa(): List<DataSiswa> = withContext(Dispatchers.IO) {
        val siswaList = mutableListOf<DataSiswa>()
        var connection: Connection? = null

        try {
            connection = getConnection()
            if (connection == null) {
                Log.e(TAG, "Failed to connect to database")
                return@withContext emptyList()
            }

            val statement = connection.createStatement()
            val query = "SELECT id, nama, alamat, telpon, created_at FROM teman ORDER BY id DESC"
            val resultSet: ResultSet = statement.executeQuery(query)

            while (resultSet.next()) {
                val siswa = DataSiswa(
                    id = resultSet.getString("id"),
                    nama = resultSet.getString("nama"),
                    alamat = resultSet.getString("alamat"),
                    telpon = resultSet.getString("telpon"),
                    createdAt = resultSet.getString("created_at")
                )
                siswaList.add(siswa)
            }

            resultSet.close()
            statement.close()

            Log.d(TAG, "Successfully loaded ${siswaList.size} siswa from database")
        } catch (e: Exception) {
            Log.e(TAG, "Error getting siswa: ${e.message}", e)
        } finally {
            connection?.close()
        }

        siswaList
    }

    /**
     * Menambahkan data siswa baru ke database
     */
    suspend fun insertSiswa(siswa: DataSiswa): Boolean = withContext(Dispatchers.IO) {
        var connection: Connection? = null
        var success = false

        try {
            connection = getConnection()
            if (connection == null) {
                Log.e(TAG, "Failed to connect to database")
                return@withContext false
            }

            val query = "INSERT INTO teman (nama, alamat, telpon) VALUES (?, ?, ?)"
            val preparedStatement = connection.prepareStatement(query)

            preparedStatement.setString(1, siswa.nama)
            preparedStatement.setString(2, siswa.alamat)
            preparedStatement.setString(3, siswa.telpon)

            val rowsAffected = preparedStatement.executeUpdate()
            success = rowsAffected > 0

            preparedStatement.close()

            if (success) {
                Log.d(TAG, "Successfully inserted siswa: ${siswa.nama}")
            } else {
                Log.e(TAG, "Failed to insert siswa")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error inserting siswa: ${e.message}", e)
        } finally {
            connection?.close()
        }

        success
    }

    /**
     * Menghapus data siswa berdasarkan ID
     */
    suspend fun deleteSiswa(id: String): Boolean = withContext(Dispatchers.IO) {
        var connection: Connection? = null
        var success = false

        try {
            connection = getConnection()
            if (connection == null) {
                Log.e(TAG, "Failed to connect to database")
                return@withContext false
            }

            val query = "DELETE FROM teman WHERE id = ?"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setString(1, id)

            val rowsAffected = preparedStatement.executeUpdate()
            success = rowsAffected > 0

            preparedStatement.close()

            if (success) {
                Log.d(TAG, "Successfully deleted siswa with id: $id")
            } else {
                Log.e(TAG, "Failed to delete siswa")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting siswa: ${e.message}", e)
        } finally {
            connection?.close()
        }

        success
    }

    /**
     * Update data siswa
     */
    suspend fun updateSiswa(siswa: DataSiswa): Boolean = withContext(Dispatchers.IO) {
        var connection: Connection? = null
        var success = false

        try {
            connection = getConnection()
            if (connection == null) {
                Log.e(TAG, "Failed to connect to database")
                return@withContext false
            }

            val query = "UPDATE teman SET nama = ?, alamat = ?, telpon = ? WHERE id = ?"
            val preparedStatement = connection.prepareStatement(query)

            preparedStatement.setString(1, siswa.nama)
            preparedStatement.setString(2, siswa.alamat)
            preparedStatement.setString(3, siswa.telpon)
            preparedStatement.setString(4, siswa.id)

            val rowsAffected = preparedStatement.executeUpdate()
            success = rowsAffected > 0

            preparedStatement.close()

            if (success) {
                Log.d(TAG, "Successfully updated siswa: ${siswa.nama}")
            } else {
                Log.e(TAG, "Failed to update siswa")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error updating siswa: ${e.message}", e)
        } finally {
            connection?.close()
        }

        success
    }

    /**
     * Test koneksi ke database
     */
    suspend fun testConnection(): Boolean = withContext(Dispatchers.IO) {
        var connection: Connection? = null
        try {
            connection = getConnection()
            val isConnected = connection != null && !connection.isClosed
            if (isConnected) {
                Log.d(TAG, "Database connection test: SUCCESS")
            } else {
                Log.e(TAG, "Database connection test: FAILED")
            }
            isConnected
        } catch (e: Exception) {
            Log.e(TAG, "Database connection test error: ${e.message}", e)
            false
        } finally {
            connection?.close()
        }
    }
}

