from django.conf.urls import url

from .views import ExchangeAuthorizationCode, FitbitClientId

urlpatterns = [
    url(r'client_id', view=FitbitClientId.as_view(), name='fitbit-client-id'),
    url(r'$', view=ExchangeAuthorizationCode.as_view(), name='fitbit-exchange-auth-code'),
]
