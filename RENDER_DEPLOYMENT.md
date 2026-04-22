# Deploy on Render - Step by Step Guide

## Prerequisites
- GitHub account
- Render account (free at https://render.com)

## Step 1: Create Render Account

1. Go to https://render.com
2. Click "Get Started for Free"
3. Sign up using your GitHub account (recommended)
4. Authorize Render to access your GitHub repositories

## Step 2: Create MySQL Database

1. In Render Dashboard, click **"New +"**
2. Select **"PostgreSQL"** (Note: Free MySQL not available, use external or upgrade)
   
   **Alternative: Use Free External MySQL**
   
   Go to https://planetscale.com or https://freemysqlhosting.net:
   - Create free MySQL database
   - Note down:
     - Host
     - Database name
     - Username
     - Password
     - Port (usually 3306)

## Step 3: Deploy Web Service

1. In Render Dashboard, click **"New +"**
2. Select **"Web Service"**
3. Connect your GitHub repository:
   - Click "Connect GitHub"
   - Select `Pratiksha2968/Grampanchayat_Portal`
4. Configure the service:

   | Setting | Value |
   |---------|-------|
   | Name | `gram-panchayat-portal` |
   | Region | Choose closest to you |
   | Branch | `main` |
   | Runtime | `Docker` |
   | Instance Type | `Free` |

5. Add Environment Variables:

   Click "Advanced" → "Add Environment Variable"

   | Key | Value |
   |-----|-------|
   | `SPRING_DATASOURCE_URL` | `jdbc:mysql://YOUR_HOST:3306/gram_panchayat_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC` |
   | `SPRING_DATASOURCE_USERNAME` | Your database username |
   | `SPRING_DATASOURCE_PASSWORD` | Your database password |
   | `JWT_SECRET` | `YourSecretKeyForJWT123456` |
   | `PORT` | `8080` |

6. Click **"Create Web Service"**

## Step 4: Wait for Deployment

- Render will build your Docker image
- This takes 5-10 minutes
- Watch the logs for any errors

## Step 5: Access Your Application

Once deployed, your app will be available at:
```
https://gram-panchayat-portal.onrender.com/gram-panchayat
```

## Troubleshooting

### Build Fails
- Check the build logs
- Ensure Dockerfile is correct
- Verify all dependencies in pom.xml

### Database Connection Error
- Verify database credentials
- Check if database host is accessible from Render
- Ensure SSL settings are correct

### Application Error
- Check application logs in Render dashboard
- Verify environment variables are set correctly

## Free Tier Limitations

- 750 hours/month free
- Service spins down after 15 minutes of inactivity
- First request after spin-down takes ~30 seconds
- 512MB RAM
- Shared CPU

## Need Help?

- Render Documentation: https://render.com/docs
- Check logs in Render Dashboard
- GitHub Issues: https://github.com/Pratiksha2968/Grampanchayat_Portal/issues
