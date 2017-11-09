from .base import *

SECRET_KEY = 'NOT_VERY_SECURE_DEVELOPMENT_SECRET_KEY'
DEBUG = True

DATABASES['default'].update({
    'NAME': 'smart_alarm',
    'USER': 'smart_alarm',
    'PASSWORD': 'requiemForADream',
    'HOST': 'smart_alarm_postgresql_host',
    'PORT': '5432',
})

INSTALLED_APPS.append('django_extensions',)
ALLOWED_HOSTS = ['10.0.2.2']
