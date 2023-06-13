/*
  DateTime.h - DateTime library - implementation
*/

#include "AtomSPK.h"
#include "M5Atom.h"

#define SCK  23
#define MISO 33
#define MOSI 19

AtomSPK::AtomSPK()
{
	M5.begin(true, false, true);
	SPI.begin(SCK, MISO, MOSI, -1);
	if (!SD.begin(-1, SPI, 40000000)) {
	   Serial.println("Card Mount Failed");
	}
	out = NULL;
	file = NULL;
	mp3 = NULL;
	id3 = NULL;
	fileIdSettingsCount = 0;
}


void AtomSPK::playMp3FromFile(String filename)
{
	if (this->isPlaying()) {
		this->stopMp3();
	}
	file = new AudioFileSourceSD(filename.c_str());
	out = new AudioOutputI2S();
	id3 = new AudioFileSourceID3(file);
	out->SetPinout(22, 21, 25);
	mp3 = new AudioGeneratorMP3();
	mp3->begin(id3, out);

}


void AtomSPK::stopMp3()
{
	if (this->isPlaying()) {
	   mp3->stop();
	   Serial.printf("MP3 done\n");
	   delay(300);
	   delete file;
	   delete out;
	   delete id3;
	   delete mp3;
	}
}

void AtomSPK::addFileIdSetting(FileIdSetting fileIdSetting)
{
	fileIdSettings[fileIdSettingsCount] = fileIdSetting;
	fileIdSettingsCount++;
}

void AtomSPK::playMp3FromId(String id)
{
	if (this->isPlaying()) {
		this->stopMp3();
	}
	for (int i = 0; i < fileIdSettingsCount; i++) {
		if (fileIdSettings[i].id.equals(id)) {
			this->playMp3FromFile(fileIdSettings[i].filename);
		}
	}
}

bool AtomSPK::isPlaying()
{
	return mp3 && mp3->isRunning();

}

void AtomSPK::loop()
{
	if (this->isPlaying() && !mp3->loop()) {
		this->stopMp3();
	}

}




