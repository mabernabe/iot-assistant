

#ifndef AtomSPK_h
#define AtomSPK_h

#include "Arduino.h"
#include "AudioFileSourceSD.h"
#include "AudioGeneratorMP3.h"
#include "AudioOutputI2S.h"
#include "AudioFileSourceID3.h"

#define MAX_ID 10

struct FileIdSetting
{
	String id;
	String filename;
};

class AtomSPK
{

  private:
	AudioGeneratorMP3 *mp3;
	AudioFileSourceSD *file;
	AudioOutputI2S *out;
	AudioFileSourceID3 *id3;
	FileIdSetting fileIdSettings[MAX_ID];
    int fileIdSettingsCount;

  public:
	AtomSPK();
	void playMp3FromFile(String filename);
	void addFileIdSetting(FileIdSetting fileIdSetting);
	void playMp3FromId(String id);
	void stopMp3();
	bool isPlaying();
	void loop();

};

#endif
