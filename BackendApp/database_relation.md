# Database Relations

## 1. Tabel: users
Fungsi: Menyimpan data calon nasabah yang mendaftar ke sistem.

| Kolom           | Tipe Data    | Keterangan                                     |
|-----------------|--------------|------------------------------------------------|
| id              | UUID / BIGINT| Primary key, ID unik untuk setiap calon nasabah|
| name            | VARCHAR(255) | Nama lengkap calon nasabah                     |
| email           | VARCHAR(255) | Email calon nasabah untuk notifikasi           |
| phone_number    | VARCHAR(15)  | Nomor telepon calon nasabah                    |
| identity_number | VARCHAR(50)  | Nomor identitas (KTP/Passport)                 |
| created_at      | TIMESTAMP    | Waktu calon nasabah mendaftar                  |
| updated_at      | TIMESTAMP    | Waktu data terakhir diperbarui                 |

## 2. Tabel: queues
Fungsi: Mengatur antrian untuk proses KYC.

| Kolom        | Tipe Data                                        | Keterangan                             |
|--------------|--------------------------------------------------|----------------------------------------|
| id           | UUID / BIGINT                                    | Primary key, ID unik untuk setiap antrian |
| user_id      | UUID / BIGINT                                    | Foreign key ke tabel users             |
| status       | ENUM ('waiting', 'in_call', 'completed', 'canceled') | Status antrian                      |
| joined_at    | TIMESTAMP                                        | Waktu masuk ke antrian                 |
| processed_at | TIMESTAMP                                        | Waktu mulai video call                 |
| completed_at | TIMESTAMP                                        | Waktu proses selesai                   |

## 3. Tabel: calls
Fungsi: Menyimpan data setiap sesi video call.

| Kolom       | Tipe Data             | Keterangan                                  |
|-------------|------------------------|---------------------------------------------|
| id          | UUID / BIGINT         | Primary key, ID unik untuk setiap sesi      |
| user_id     | UUID / BIGINT         | Foreign key ke tabel users                  |
| admin_id    | UUID / BIGINT         | Foreign key ke tabel admins                 |
| room_name   | VARCHAR(255)          | Nama/ID unik ruang video call di Twilio     |
| start_time  | TIMESTAMP             | Waktu panggilan dimulai                     |
| end_time    | TIMESTAMP             | Waktu panggilan selesai                     |
| call_status | ENUM ('success', 'failed') | Status panggilan berhasil/gagal        |

## 4. Tabel: admins
Fungsi: Menyimpan data petugas KYC yang memproses video call.

| Kolom      | Tipe Data                           | Keterangan                               |
|------------|-------------------------------------|------------------------------------------|
| id         | UUID / BIGINT                       | Primary key, ID unik untuk setiap admin  |
| name       | VARCHAR(255)                        | Nama lengkap admin                       |
| email      | VARCHAR(255)                        | Email admin untuk login atau notifikasi  |
| role       | ENUM ('kyc_officer', 'supervisor')  | Peran admin dalam sistem                 |
| created_at | TIMESTAMP                           | Waktu akun dibuat                        |

## 5. Tabel: logs
Fungsi: Menyimpan catatan aktivitas untuk audit dan pelacakan proses.

| Kolom     | Tipe Data    | Keterangan                                      |
|-----------|--------------|--------------------------------------------------|
| id        | UUID / BIGINT| Primary key, ID unik untuk setiap log            |
| user_id   | UUID / BIGINT| Foreign key ke tabel users                       |
| admin_id  | UUID / BIGINT| Foreign key ke tabel admins                      |
| action    | VARCHAR(255) | Deskripsi tindakan (misalnya, "start_call")      |
| timestamp | TIMESTAMP    | Waktu tindakan dilakukan                         |

## Relasi Antar Tabel

1. users ↔ queues:
   - Relasi 1:1. Setiap calon nasabah dapat memiliki satu status antrian aktif pada satu waktu.

2. users ↔ calls:
   - Relasi 1:N. Seorang pengguna dapat memiliki beberapa sesi video call (misalnya, jika proses gagal).

3. admins ↔ calls:
   - Relasi 1:N. Seorang admin dapat memproses banyak sesi KYC.

4. logs:
   - Berhubungan dengan users dan admins untuk melacak tindakan masing-masing pihak.