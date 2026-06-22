package io.akari.akaribox.aidl;

import io.akari.akaribox.aidl.IServiceCallback;

interface IService {
  int getStatus();
  void registerCallback(in IServiceCallback callback);
  oneway void unregisterCallback(in IServiceCallback callback);
}