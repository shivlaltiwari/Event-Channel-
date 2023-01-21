import 'package:flutter/services.dart';

const EventChannel CHANNEL = EventChannel("com.np.tiwari");

class EventChannelData {
  // x-axis
  final double x;

  // y-axis
  final double y;

  // z-axis
  final double z;

  EventChannelData(this.x, this.y, this.z);

  double getZ() => this.z;

  double getY() => this.y;

  double getX() => this.x;

  @override
  String toString() => "[EventChannelData(x : $x, y: $y,z: $z)]";
}

Stream<EventChannelData>? _magneticEvent;

EventChannelData _listOfValue(List<double> data) {
  return EventChannelData(data[0], data[1], data[2]);
}

Stream<EventChannelData> get eventData {
  _magneticEvent ??= CHANNEL
      .receiveBroadcastStream()
      .map((event) => _listOfValue(event.cast<double>()));

  return _magneticEvent!;
}
