import environ

env = environ.Env()

SOCIAL_AUTH_FITBIT_KEY = env('FITBIT_CLIENT_ID')
SOCIAL_AUTH_FITBIT_SECRET = env('FITBIT_CLIENT_SECRET')
SOCIAL_AUTH_FITBIT_SCOPE = [
  'profile',
  'settings',
  'sleep',
]
SOCIAL_AUTH_FITBIT_ACCESS_TOKEN_URL = "https://api.fitbit.com/oauth2/token"
