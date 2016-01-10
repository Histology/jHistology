#include "java.h"
#include "tetgen.h"

JNIEXPORT void JNICALL Java_org_yuzhakov_histology_Tetgen_tetrahedralize
	(JNIEnv * je, jobject jo, jstring params){

	jclass javaClass;
	jmethodID getNumberOfVertexesMethodID;
	jmethodID setNumberOfVertexesMethodID;
	jmethodID getNumberOfFacesMethodID;
	jmethodID getVertexesMethodID;
	jmethodID setVertexesMethodID;
	jmethodID getFacesSizesMethodID;
	jmethodID getFacesMethodID;
	jmethodID setNumberOfTetrahedronsMethodID;
	jmethodID setTetrahedronsMethodID;


	javaClass = je->GetObjectClass( jo );
	getNumberOfVertexesMethodID = je->GetMethodID( javaClass, "getNumberOfVertexes", "()I" );
	setNumberOfVertexesMethodID = je->GetMethodID( javaClass, "setNumberOfVertexes", "(I)V" );
	getNumberOfFacesMethodID = je->GetMethodID( javaClass, "getNumberOfFaces", "()I" );
	getVertexesMethodID = je->GetMethodID( javaClass, "getVertexes", "()[D" );
	setVertexesMethodID = je->GetMethodID( javaClass, "setVertexes", "([D)V" );
	getFacesSizesMethodID = je->GetMethodID( javaClass, "getFacesSizes", "()[I" );
	getFacesMethodID = je->GetMethodID( javaClass, "getFaces", "()[I" );
	setNumberOfTetrahedronsMethodID = je->GetMethodID( javaClass, "setNumberOfTetrahedrons", "(I)V" );
	setTetrahedronsMethodID = je->GetMethodID( javaClass, "setTetrahedrons", "([I)V" );

	int numberOfVertexes = je->CallIntMethod(jo, getNumberOfVertexesMethodID);
	jobject jVertexesObj = je->CallObjectMethod(jo, getVertexesMethodID);
	jdoubleArray* jVertexes = reinterpret_cast<jdoubleArray*>(&jVertexesObj);
	double* vertexes = je->GetDoubleArrayElements(*jVertexes, false);
	
	int numberOfFaces = je->CallIntMethod(jo, getNumberOfFacesMethodID);
	jobject jFaceSizesObj = je->CallObjectMethod(jo, getFacesSizesMethodID);
	jintArray* jFaceSizes = reinterpret_cast<jintArray*>(&jFaceSizesObj);
	long* faceSizes = je->GetIntArrayElements(*jFaceSizes, false);
	jobject jFacesObj = je->CallObjectMethod(jo, getFacesMethodID);
	jintArray* jFaces = reinterpret_cast<jintArray*>(&jFacesObj);
	long* faces = je->GetIntArrayElements(*jFaces, false);

	tetgenio in, out;
	tetgenio::facet *facet;
	tetgenio::polygon *polygon;

	// All indices start from 1.
	in.firstnumber = 0;

	in.numberofpoints = numberOfVertexes;
	in.pointlist = new REAL[numberOfVertexes*3];

	for (int i = 0; i < numberOfVertexes*3; i++) {
		in.pointlist[i] = vertexes[i];
	}

	in.numberoffacets = numberOfFaces;
	in.facetlist = new tetgenio::facet[numberOfFaces];
	//in.facetmarkerlist = new int[in.numberoffacets];

	int faceSize;
	int faceIndex = 0;
	for (int i = 0; i < numberOfFaces; ++i){
		facet = &in.facetlist[i];
		facet->numberofpolygons = 1;
		facet->polygonlist = new tetgenio::polygon[facet->numberofpolygons];
		facet->numberofholes = 0;
		facet->holelist = NULL;
		polygon = &facet->polygonlist[0];
		faceSize = faceSizes[i];
		polygon->numberofvertices = faceSize;
		polygon->vertexlist = new int[faceSize];
		for (int k = 0; k < faceSize;++k){
			polygon->vertexlist[k] = faces[faceIndex];
			++faceIndex;
		}
	}

	// Output the PLC to files 'barin.node' and 'barin.poly'.
	//in.save_nodes("in");
	//in.save_poly("in");

	// Tetrahedralize the PLC. Switches are chosen to read a PLC (p),
	// without output (Q).

	tetgenbehavior b;
	b.parse_commandline("pQ");
	tetrahedralize(&b, &in, &out);

	// Output mesh to files 'barout.node', 'barout.ele' and 'barout.face'.
	//out.save_nodes("out");
	//out.save_elements("out");
	//out.save_faces("out");

	double* newVertexes = out.pointlist;
	numberOfVertexes = out.numberofpoints;
	int vertexSize = numberOfVertexes*3;
	int numberOfTetrahedrons = out.numberoftetrahedra;
	int tetrahedronArraySize = numberOfTetrahedrons*4;
	int* tetrahedronsInt = out.tetrahedronlist;
	jint* tetrahedronsJInt = new jint[tetrahedronArraySize];
	for (int i = 0; i < tetrahedronArraySize; ++i){
		tetrahedronsJInt[i] = tetrahedronsInt[i];
	}

	//set vertexes
	je->CallVoidMethod( jo, setNumberOfVertexesMethodID, numberOfVertexes);
	jdoubleArray jVertex = je->NewDoubleArray(vertexSize);
	je->SetDoubleArrayRegion(jVertex,0,vertexSize,newVertexes);
	je->CallVoidMethod( jo, setVertexesMethodID, jVertex);

	//set tetrahedrons
	je->CallVoidMethod( jo, setNumberOfTetrahedronsMethodID, numberOfTetrahedrons);
	jintArray jTetrahedrons = je->NewIntArray(tetrahedronArraySize);
	je->SetIntArrayRegion(jTetrahedrons,0,tetrahedronArraySize,tetrahedronsJInt);
	je->CallVoidMethod( jo, setTetrahedronsMethodID, jTetrahedrons);

	//printf("done");


	//printf("vertexes: %d \n", numberOfVertexes);
	//printf("vertex[4]: %f \n", vertexes[4]);
	//printf("faces: %d \n", numberOfFaces);
	//printf("face[0] size: %d \n", faceSizes[0]);
	//printf("face[0]: %d \n", faces[0]);
	je->ReleaseIntArrayElements(*jFaceSizes,faceSizes,0);
	je->ReleaseIntArrayElements(*jFaces,faces,0);
	je->ReleaseDoubleArrayElements(*jVertexes,vertexes,0);
}