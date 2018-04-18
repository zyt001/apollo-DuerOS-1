// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: CarlifeProtocolVersionProto.proto

#ifndef PROTOBUF_CarlifeProtocolVersionProto_2eproto__INCLUDED
#define PROTOBUF_CarlifeProtocolVersionProto_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 2005000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 2005000 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/extension_set.h>
#include <google/protobuf/unknown_field_set.h>
// @@protoc_insertion_point(includes)

namespace com {
namespace baidu {
namespace carlife {
namespace protobuf {

// Internal implementation detail -- do not call these.
void  protobuf_AddDesc_CarlifeProtocolVersionProto_2eproto();
void protobuf_AssignDesc_CarlifeProtocolVersionProto_2eproto();
void protobuf_ShutdownFile_CarlifeProtocolVersionProto_2eproto();

class CarlifeProtocolVersion;

// ===================================================================

class CarlifeProtocolVersion : public ::google::protobuf::Message {
 public:
  CarlifeProtocolVersion();
  virtual ~CarlifeProtocolVersion();

  CarlifeProtocolVersion(const CarlifeProtocolVersion& from);

  inline CarlifeProtocolVersion& operator=(const CarlifeProtocolVersion& from) {
    CopyFrom(from);
    return *this;
  }

  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _unknown_fields_;
  }

  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return &_unknown_fields_;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const CarlifeProtocolVersion& default_instance();

  void Swap(CarlifeProtocolVersion* other);

  // implements Message ----------------------------------------------

  CarlifeProtocolVersion* New() const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const CarlifeProtocolVersion& from);
  void MergeFrom(const CarlifeProtocolVersion& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // required int32 majorVersion = 1;
  inline bool has_majorversion() const;
  inline void clear_majorversion();
  static const int kMajorVersionFieldNumber = 1;
  inline ::google::protobuf::int32 majorversion() const;
  inline void set_majorversion(::google::protobuf::int32 value);

  // required int32 minorVersion = 2;
  inline bool has_minorversion() const;
  inline void clear_minorversion();
  static const int kMinorVersionFieldNumber = 2;
  inline ::google::protobuf::int32 minorversion() const;
  inline void set_minorversion(::google::protobuf::int32 value);

  // @@protoc_insertion_point(class_scope:com.baidu.carlife.protobuf.CarlifeProtocolVersion)
 private:
  inline void set_has_majorversion();
  inline void clear_has_majorversion();
  inline void set_has_minorversion();
  inline void clear_has_minorversion();

  ::google::protobuf::UnknownFieldSet _unknown_fields_;

  ::google::protobuf::int32 majorversion_;
  ::google::protobuf::int32 minorversion_;

  mutable int _cached_size_;
  ::google::protobuf::uint32 _has_bits_[(2 + 31) / 32];

  friend void  protobuf_AddDesc_CarlifeProtocolVersionProto_2eproto();
  friend void protobuf_AssignDesc_CarlifeProtocolVersionProto_2eproto();
  friend void protobuf_ShutdownFile_CarlifeProtocolVersionProto_2eproto();

  void InitAsDefaultInstance();
  static CarlifeProtocolVersion* default_instance_;
};
// ===================================================================


// ===================================================================

// CarlifeProtocolVersion

// required int32 majorVersion = 1;
inline bool CarlifeProtocolVersion::has_majorversion() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
inline void CarlifeProtocolVersion::set_has_majorversion() {
  _has_bits_[0] |= 0x00000001u;
}
inline void CarlifeProtocolVersion::clear_has_majorversion() {
  _has_bits_[0] &= ~0x00000001u;
}
inline void CarlifeProtocolVersion::clear_majorversion() {
  majorversion_ = 0;
  clear_has_majorversion();
}
inline ::google::protobuf::int32 CarlifeProtocolVersion::majorversion() const {
  return majorversion_;
}
inline void CarlifeProtocolVersion::set_majorversion(::google::protobuf::int32 value) {
  set_has_majorversion();
  majorversion_ = value;
}

// required int32 minorVersion = 2;
inline bool CarlifeProtocolVersion::has_minorversion() const {
  return (_has_bits_[0] & 0x00000002u) != 0;
}
inline void CarlifeProtocolVersion::set_has_minorversion() {
  _has_bits_[0] |= 0x00000002u;
}
inline void CarlifeProtocolVersion::clear_has_minorversion() {
  _has_bits_[0] &= ~0x00000002u;
}
inline void CarlifeProtocolVersion::clear_minorversion() {
  minorversion_ = 0;
  clear_has_minorversion();
}
inline ::google::protobuf::int32 CarlifeProtocolVersion::minorversion() const {
  return minorversion_;
}
inline void CarlifeProtocolVersion::set_minorversion(::google::protobuf::int32 value) {
  set_has_minorversion();
  minorversion_ = value;
}


// @@protoc_insertion_point(namespace_scope)

}  // namespace protobuf
}  // namespace carlife
}  // namespace baidu
}  // namespace com

#ifndef SWIG
namespace google {
namespace protobuf {


}  // namespace google
}  // namespace protobuf
#endif  // SWIG

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_CarlifeProtocolVersionProto_2eproto__INCLUDED