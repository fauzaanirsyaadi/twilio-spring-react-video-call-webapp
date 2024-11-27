# Bank KYC Video Call

A web application to facilitate **Know Your Customer (KYC)** process for bank customers using video calls with an automated queue system.

---

## **Features**

1. **KYC Queue System**
   - Customers register in the queue for KYC video calls.
   - Real-time status updates: *Waiting*, *In-Call*, *Completed*.
   - Admins can manage the queue via a dashboard.

2. **Video Call Integration**
   - Powered by Twilio for seamless video call sessions.
   - Basic controls: mute/unmute audio and video.
   - Document sharing during the call (optional).

3. **Admin Dashboard**
   - View and manage customer queues.
   - Start and end video call sessions.
   - Access logs and KYC history.

4. **Notifications**
   - Notify customers when their turn is approaching.
   - Email or SMS reminders for upcoming sessions.

5. **Audit Logging**
   - All KYC sessions are logged for tracking and auditing purposes.

---

## **Technologies Used**

- **Backend**: Java + Spring Boot
- **Frontend**: React.js + Chakra UI (with TypeScript)
- **Database**: PostgreSQL
- **Video Call**: Twilio API
- **Real-time Updates**: WebSocket (or Polling fallback)

---

## **Setup Instructions**

### **1. Clone Repository*
```bash
git clone 
