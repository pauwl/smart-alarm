from django.conf.urls import url

from smart_alarm.api.auth.fitbit.views.exchange_authorization_code_endpoint import ExchangeAuthorizationCode
from smart_alarm.api.auth.fitbit.views.fitbit_client_id_endpoint import FitbitClientId

urlpatterns = [
    url(r'client_id', view=FitbitClientId.as_view(), name='fitbit-client-id'),
    url(r'code', view=ExchangeAuthorizationCode.as_view(), name='fitbit-exchange-auth-code'),
]
