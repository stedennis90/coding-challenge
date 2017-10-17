// Refactorizado

public function post_confirm(){
	$id = Input::get('service_id');
	$servicio = Service::find($id);
	Response response;
	
	if (servicio!=NULL){
		if($servicio->status_id != '6'){
			if($servicio->driver_id==NULL && $servicio->status_id == '1'){
				$driver_id = Input::get('driver_id');
				
				$servicio = Service::update($id, array(
						'driver_id' => $driver_id,
						'status_id' => '2'
				));
				
				Driver::update($driver_id, array(
						'available' => '0'
				));
				
				$servicio = Service.update($id, array(
						'car_id' => Driver::find($driver_id)->car_id
				)); 
				
				$result = notify (servicio, $pushMessage);
				response = makeResponse('0');
			} else {
				response = makeResponse('1');
			}
		} else {
			response = makeResponse('2');
		} 
	} else {
		response = makeResponse('3');
	}
	
	return response;
}

public function makeResponse(errorCode){
	return Response::json(array('error' => errorCode));
}

public function notify(servicio){
	$push = Push.make();
	$pushMessage = 'Tu servicio ha sido confirmado!';
	$data = array('serviceId' => $servicio->id);
	$title = 'Open'
	if (servicio->user->type == '1'){
		return $push->ios($servicio->user->uuid, $pushMessage, 1, 'honk.wav', $title, $data);
	} else {
		return $push->android2($servicio->user->uuid, $pushMessage, 1, 'default', $title, $data );
	}
}
