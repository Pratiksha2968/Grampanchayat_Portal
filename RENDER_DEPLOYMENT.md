# Render Deployment Guide

## Prerequisites
- GitHub account
- Render account (https://render.com)

## Step 1: Push Code to GitHub
Make sure your code is pushed to GitHub:
```bash
git add .
git commit -m "Update for Render deployment"
git push
```

## Step 2: Create MySQL Database on Render

1. Go to https://dashboard.render.com
2. Click "New" → "PostgreSQL" (or MySQL if available)
3. Note: Render free tier only offers PostgreSQL, not MySQL

### Option A: Use PostgreSQL (Recommended for Render)

Update `pom.xml` to add PostgreSQL driver:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

Update `application.properties`:
```properties
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/gram_panchayat_db}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### Option B: Use External MySQL Database

Use a free MySQL database from:
- Railway (https://railway.app)
- PlanetScale (https://planetscale.com)
- Neon (https://neon.tech) - PostgreSQL

## Step 3: Create Web Service on Render

1. Go to https://dashboard.render.com
2. Click "New" → "Web Service"
3. Connect your GitHub repository
4. Configure:
   - **Name**: gram-panchayat
   - **Environment**: Docker
   - **Region**: Choose closest to you
   - **Branch**: main
   - **Plan**: Free

5. Add Environment Variables:
   - `SPRING_DATASOURCE_URL` - Your database connection string
   - `SPRING_DATASOURCE_USERNAME` - Database username
   - `SPRING_DATASOURCE_PASSWORD` - Database password
   - `JWT_SECRET` - Any random string for JWT signing
   - `PORT` - 8080

6. Click "Create Web Service"

## Step 4: Wait for Deployment

Render will:
1. Pull your code from GitHub
2. Build the Docker image
3. Deploy the application

This may take 5-10 minutes.

## Step 5: Access Your Application

Once deployed, your app will be available at:
```
https://your-app-name.onrender.com/gram-panchayat
```

## Troubleshooting

### Port Binding Error
Make sure your app binds to the PORT environment variable:
```properties
server.port=${PORT:8080}
```

### Database Connection Error
- Verify database credentials
- Check if database allows external connections
- Ensure SSL is configured if required

### Build Timeout
- Increase build timeout in Render settings
- Optimize Dockerfile for faster builds

### Memory Issues
- Upgrade to a paid plan for more memory
- Optimize JVM settings:
```dockerfile
CMD ["java", "-Xmx512m", "-jar", "app.war"]
```

## Free Tier Limitations

Render Free Tier:
- 750 hours/month
- 512MB RAM
- Services spin down after inactivity
- Limited database options

## Alternative Platforms

If Render doesn't work, try:
- **Railway** (https://railway.app) - Easier database setup
- **Fly.io** (https://fly.io) - Good for Java apps
- **Heroku** (https://heroku.com) - Classic choice
- **Koyeb** (https://www.koyeb.com) - Simple deployment
